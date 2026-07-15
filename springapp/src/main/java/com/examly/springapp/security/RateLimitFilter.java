package com.examly.springapp.security;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Rate-limiting filter for the feedback submission endpoint.
 * FR9.3: Prevents excessive submissions from the same IP address.
 * Uses a simple sliding window approach: max N requests per IP within a time window.
 */
@Component
public class RateLimitFilter extends OncePerRequestFilter {

    private static final int MAX_REQUESTS = 10;
    private static final long WINDOW_MS = 60 * 1000; // 1 minute

    private final Map<String, WindowCounter> counters = new ConcurrentHashMap<>();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                     FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();
        String method = request.getMethod();

        // Only rate-limit POST /api/v1/feedback
        if ("POST".equalsIgnoreCase(method) && path.equals("/api/v1/feedback")) {
            String clientIp = getClientIp(request);
            WindowCounter counter = counters.computeIfAbsent(clientIp, k -> new WindowCounter());

            synchronized (counter) {
                long now = System.currentTimeMillis();
                if (now - counter.windowStart > WINDOW_MS) {
                    counter.windowStart = now;
                    counter.count = new AtomicInteger(0);
                }
                if (counter.count.incrementAndGet() > MAX_REQUESTS) {
                    response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                    response.setContentType("application/json");
                    response.getWriter().write(
                            "{\"status\":429,\"error\":\"Too Many Requests\"," +
                            "\"message\":\"Rate limit exceeded. Please try again later.\"}");
                    return;
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    private String getClientIp(HttpServletRequest request) {
        String xff = request.getHeader("X-Forwarded-For");
        if (xff != null && !xff.isBlank()) {
            return xff.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }

    private static class WindowCounter {
        long windowStart = System.currentTimeMillis();
        AtomicInteger count = new AtomicInteger(0);
    }
}

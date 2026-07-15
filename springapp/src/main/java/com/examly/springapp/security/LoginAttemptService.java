package com.examly.springapp.security;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;

/**
 * Brute-force prevention: tracks failed login attempts per username.
 * Locks the account after MAX_ATTEMPTS failed tries for LOCK_TIME_MS.
 * FR12.1: Account lockout after 5 failed attempts.
 */
@Component
public class LoginAttemptService {

    private static final int MAX_ATTEMPTS = 5;
    private static final long LOCK_TIME_MS = 15 * 60 * 1000; // 15 minutes

    private final Map<String, AtomicInteger> attemptsCache = new ConcurrentHashMap<>();
    private final Map<String, Long> lockTimeCache = new ConcurrentHashMap<>();

    public void loginSucceeded(String username) {
        attemptsCache.remove(username);
        lockTimeCache.remove(username);
    }

    public void loginFailed(String username) {
        AtomicInteger attempts = attemptsCache.computeIfAbsent(username, k -> new AtomicInteger(0));
        int current = attempts.incrementAndGet();
        if (current >= MAX_ATTEMPTS) {
            lockTimeCache.put(username, System.currentTimeMillis());
        }
    }

    public boolean isBlocked(String username) {
        Long lockTime = lockTimeCache.get(username);
        if (lockTime == null) {
            return false;
        }
        if (System.currentTimeMillis() - lockTime > LOCK_TIME_MS) {
            // Lock period expired — reset
            attemptsCache.remove(username);
            lockTimeCache.remove(username);
            return false;
        }
        return true;
    }

    public int getRemainingAttempts(String username) {
        AtomicInteger attempts = attemptsCache.get(username);
        if (attempts == null) {
            return MAX_ATTEMPTS;
        }
        return Math.max(0, MAX_ATTEMPTS - attempts.get());
    }
}

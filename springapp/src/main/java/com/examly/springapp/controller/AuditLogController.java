package com.examly.springapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.model.AuditLog;
import com.examly.springapp.service.AuditLogService;

@RestController
@RequestMapping("/api/v1/admin/audit-logs")
public class AuditLogController {

    @Autowired
    private AuditLogService auditLogService;

    /**
     * FR10: View audit trail — all logged admin actions.
     */
    @GetMapping
    public ResponseEntity<List<AuditLog>> getAllAuditLogs() {
        List<AuditLog> logs = auditLogService.getAllLogs();
        return new ResponseEntity<>(logs, HttpStatus.OK);
    }
}

package com.examly.springapp.service;

import java.util.List;

import com.examly.springapp.model.AuditLog;

public interface AuditLogService {

    AuditLog createLog(Long adminId, String action, String entityType, Long entityId);

    List<AuditLog> getAllLogs();

    List<AuditLog> getLogsByAdminId(Long adminId);

    List<AuditLog> getLogsByEntity(String entityType, Long entityId);
}

package com.examly.springapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.examly.springapp.model.AuditLog;
import com.examly.springapp.repository.AuditLogRepository;

@Service
public class AuditLogServiceImpl implements AuditLogService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Override
    @Transactional
    public AuditLog createLog(Long adminId, String action, String entityType, Long entityId) {
        AuditLog log = new AuditLog(adminId, action, entityType, entityId);
        return auditLogRepository.save(log);
    }

    @Override
    public List<AuditLog> getAllLogs() {
        return auditLogRepository.findAllByOrderByTimestampDesc();
    }

    @Override
    public List<AuditLog> getLogsByAdminId(Long adminId) {
        return auditLogRepository.findByAdminIdOrderByTimestampDesc(adminId);
    }

    @Override
    public List<AuditLog> getLogsByEntity(String entityType, Long entityId) {
        return auditLogRepository.findByEntityTypeAndEntityIdOrderByTimestampDesc(entityType, entityId);
    }
}

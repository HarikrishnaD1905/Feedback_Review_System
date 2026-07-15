package com.examly.springapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.AuditLog;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    List<AuditLog> findAllByOrderByTimestampDesc();

    List<AuditLog> findByAdminIdOrderByTimestampDesc(Long adminId);

    List<AuditLog> findByEntityTypeAndEntityIdOrderByTimestampDesc(String entityType, Long entityId);
}

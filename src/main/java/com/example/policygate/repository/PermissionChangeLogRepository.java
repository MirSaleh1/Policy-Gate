package com.example.policygate.repository;

import com.example.policygate.entity.PermissionChangeLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionChangeLogRepository extends JpaRepository<PermissionChangeLog, Long> {
}

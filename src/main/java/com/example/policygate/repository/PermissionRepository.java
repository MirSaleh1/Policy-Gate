package com.example.policygate.repository;

import com.example.policygate.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission,Long> {
    boolean existsByName(String name);
}

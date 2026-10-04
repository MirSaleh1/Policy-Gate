package com.example.policygate.service;

import com.example.policygate.entity.Permission;
import com.example.policygate.entity.PermissionChangeLog;
import com.example.policygate.exception.BusinessException;
import com.example.policygate.exception.ExceptionConstants;
import com.example.policygate.mapper.PermissionMapper;
import com.example.policygate.model.response.PermissionResponse;
import com.example.policygate.repository.PermissionChangeLogRepository;
import com.example.policygate.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionService {

    private final PermissionRepository permissionRepository;
    private final PermissionChangeLogRepository logRepository;

    @Transactional
    @CacheEvict(value = "permissions", allEntries = true)
    public PermissionResponse createPermission(String name) {
        if (permissionRepository.existsByName(name)) {
            throw new BusinessException(ExceptionConstants.PERMISSION_ALREADY_EXISTS);
        }
        Permission permission = new Permission();
        permission.setName(name);
        Permission saved = permissionRepository.save(permission);
        
        saveLog("CREATED", saved.getName(), null);
        return PermissionMapper.mapToPermissionResponse(saved);
    }

    @Transactional
    @CacheEvict(value = "permissions", allEntries = true)
    public PermissionResponse updatePermission(Long id, String name) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ExceptionConstants.PERMISSION_NOT_FOUND));
        if (permissionRepository.existsByName(name)) {
            throw new BusinessException(ExceptionConstants.PERMISSION_ALREADY_EXISTS);
        }
        String oldName = permission.getName();
        permission.setName(name);
        Permission saved = permissionRepository.save(permission);
        
        saveLog("UPDATED (from " + oldName + ")", saved.getName(), null);
        return PermissionMapper.mapToPermissionResponse(saved);
    }

    @Cacheable(value = "permissions")
    public List<PermissionResponse> getAllPermissions(){
        return permissionRepository.findAll().stream().map(PermissionMapper::mapToPermissionResponse).toList();
    }
    
    private void saveLog(String action, String permissionName, String roleName) {
        String currentUser = "System";
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        }
        PermissionChangeLog log = PermissionChangeLog.builder()
                .action(action)
                .permissionName(permissionName)
                .roleName(roleName)
                .changedBy(currentUser)
                .changeDate(LocalDateTime.now())
                .build();
        logRepository.save(log);
    }
}

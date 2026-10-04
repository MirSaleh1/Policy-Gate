package com.example.policygate.service;

import com.example.policygate.entity.Permission;
import com.example.policygate.entity.PermissionChangeLog;
import com.example.policygate.entity.Role;
import com.example.policygate.exception.BusinessException;
import com.example.policygate.exception.ExceptionConstants;
import com.example.policygate.mapper.RoleMapper;
import com.example.policygate.model.response.RoleResponse;
import com.example.policygate.repository.PermissionChangeLogRepository;
import com.example.policygate.repository.PermissionRepository;
import com.example.policygate.repository.RoleRepository;
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
public class RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final PermissionChangeLogRepository logRepository;

    @Transactional
    @CacheEvict(value = "roles", allEntries = true)
    public RoleResponse createRole(String name) {
        if (roleRepository.existsByName(name)) {
            throw new BusinessException(ExceptionConstants.ROLE_ALREADY_EXISTS);
        }
        Role role = new Role();
        role.setName(name);
        return RoleMapper.toRoleResponse(roleRepository.save(role));
    }

    @Transactional
    @CacheEvict(value = "roles", allEntries = true)
    public RoleResponse updateRole(Long id, String name) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ExceptionConstants.ROLE_NOT_FOUND));
        if (roleRepository.existsByName(name)) {
            throw new BusinessException(ExceptionConstants.ROLE_ALREADY_EXISTS);
        }
        role.setName(name);
        return RoleMapper.toRoleResponse(roleRepository.save(role));
    }

    @Transactional
    @CacheEvict(value = "roles", allEntries = true)
    public RoleResponse addPermissionToRole(Long roleId, Long permissionId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new BusinessException(ExceptionConstants.ROLE_NOT_FOUND));
        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new BusinessException(ExceptionConstants.PERMISSION_NOT_FOUND));
        role.getPermissions().add(permission);
        
        saveLog("ASSIGNED_TO_ROLE", permission.getName(), role.getName());
        return RoleMapper.toRoleResponse(roleRepository.save(role));
    }

    @Cacheable(value = "roles")
    public List<RoleResponse> getAllRoles(){
        return roleRepository.findAll().stream().map(RoleMapper::toRoleResponse).toList();
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

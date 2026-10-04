package com.example.policygate.controller;

import com.example.policygate.model.response.RoleResponse;
import com.example.policygate.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/roles")
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_WRITE')")
    public RoleResponse creatRole(@RequestParam String name){
        return roleService.createRole(name);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_WRITE')")
    public RoleResponse updateRole(@PathVariable Long id, @RequestParam String name){
        return roleService.updateRole(id, name);
    }

    @PostMapping("/{roleId}/permissions/{permissionId}")
    @PreAuthorize("hasAuthority('ROLE_WRITE')")
    public RoleResponse addPermissionToRole(@PathVariable Long roleId, @PathVariable Long permissionId){
        return roleService.addPermissionToRole(roleId, permissionId);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_READ')")
    public List<RoleResponse> getAllRole(){
        return roleService.getAllRoles();
    }

    }


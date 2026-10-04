package com.example.policygate.controller;

import com.example.policygate.model.response.PermissionResponse;
import com.example.policygate.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/permissions")
public class PermissionController {

    private final PermissionService permissionService;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_WRITE')")
    public PermissionResponse createPermission(@RequestParam String name){
        return permissionService.createPermission(name);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_WRITE')")
    public PermissionResponse updatePermission(@PathVariable Long id, @RequestParam String name){
        return permissionService.updatePermission(id, name);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_READ')")
    public List<PermissionResponse> getAllPermissions(){
        return permissionService.getAllPermissions();
    }
}

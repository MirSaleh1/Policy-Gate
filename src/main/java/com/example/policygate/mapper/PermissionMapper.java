package com.example.policygate.mapper;

import com.example.policygate.entity.Permission;
import com.example.policygate.model.response.PermissionResponse;

public interface PermissionMapper {

    static PermissionResponse mapToPermissionResponse(Permission permission){
        return new PermissionResponse(permission.getId(),permission.getName());
    }
}

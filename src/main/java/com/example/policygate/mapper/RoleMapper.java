package com.example.policygate.mapper;

import com.example.policygate.entity.Role;
import com.example.policygate.model.response.RoleResponse;

public interface RoleMapper {

    static RoleResponse toRoleResponse(Role role){
        return new RoleResponse(role.getId(),role.getName());
    }
}

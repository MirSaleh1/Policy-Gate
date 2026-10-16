package com.example.policygate.service;

import com.example.policygate.entity.Role;
import com.example.policygate.entity.User;
import com.example.policygate.exception.BusinessException;
import com.example.policygate.exception.ExceptionConstants;
import com.example.policygate.mapper.UserMapper;
import com.example.policygate.model.response.UserResponse;
import com.example.policygate.repository.RoleRepository;
import com.example.policygate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Transactional
    @CacheEvict(value="users", key="#result.email")
    public UserResponse assignRoleToUser(Long userId, Long roleId){
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new BusinessException(ExceptionConstants.ROLE_NOT_FOUND));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ExceptionConstants.USER_NOT_FOUND));
        user.getRoles().add(role);
       return UserMapper.toUserResponse(userRepository.save(user));
    }
    @Transactional
    @CacheEvict(value="users", key="#result.email")
    public UserResponse removeRoleFromUser(Long userId, Long roleId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ExceptionConstants.USER_NOT_FOUND));
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new BusinessException(ExceptionConstants.ROLE_NOT_FOUND));
        user.getRoles().remove(role);
        return UserMapper.toUserResponse(userRepository.save(user));
    }
}

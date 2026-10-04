package com.example.policygate.service;


import com.example.policygate.entity.Role;
import com.example.policygate.entity.User;
import com.example.policygate.exception.BusinessException;
import com.example.policygate.exception.ExceptionConstants;
import com.example.policygate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Cacheable(value = "users",key = "#email")
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->new UsernameNotFoundException(ExceptionConstants.USER_NOT_FOUND.getMessage()));

        Set<Role> unproxiedRoles = new HashSet<>();
        for (Role r : user.getRoles()) {
            Role newRole = new Role();
            newRole.setId(r.getId());
            newRole.setName(r.getName());
            newRole.setPermissions(new HashSet<>(r.getPermissions()));
            unproxiedRoles.add(newRole);
        }
        User cachedUser = new User();
        cachedUser.setId(user.getId());
        cachedUser.setUsername(user.getUsername());
        cachedUser.setEmail(user.getEmail());
        cachedUser.setPassword(user.getPassword());
        cachedUser.setRoles(unproxiedRoles);

        return cachedUser;
    }
}

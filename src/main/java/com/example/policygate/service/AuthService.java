package com.example.policygate.service;


import com.example.policygate.entity.Role;
import com.example.policygate.entity.User;
import com.example.policygate.exception.BusinessException;
import com.example.policygate.exception.ExceptionConstants;
import com.example.policygate.model.request.LoginRequest;
import com.example.policygate.model.request.RegisterRequest;
import com.example.policygate.model.request.TokenRequest;
import com.example.policygate.model.response.TokenResponse;
import com.example.policygate.repository.RoleRepository;
import com.example.policygate.repository.UserRepository;
import com.example.policygate.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;


    public TokenResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new BusinessException(ExceptionConstants.USER_ALREADY_EXISTS);
        }
        Role defaultRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new BusinessException(ExceptionConstants.ROLE_NOT_FOUND));

        User user = User.builder()
                .username(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .roles(Set.of(defaultRole))
                .build();

        userRepository.save(user);

        String accessToken = jwtUtil.generateToken(user.getEmail());
        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());

        return new TokenResponse(accessToken, refreshToken);
    }

    public TokenResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        User user =  (User) authentication.getPrincipal();

        String accessToken = jwtUtil.generateToken(user.getEmail());
        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());

        return new TokenResponse(accessToken, refreshToken);
    }

    public TokenResponse refreshToken(TokenRequest request) {
        String reqRefreshToken = request.refreshToken();
        String username = jwtUtil.extractUsername(reqRefreshToken);
        
        if (username != null) {
            User user = userRepository.findByEmail(username)
                    .orElseThrow(() -> new BusinessException(ExceptionConstants.USER_NOT_FOUND));
            
            if (jwtUtil.validateToken(reqRefreshToken, user.getEmail())) {
                String newAccessToken = jwtUtil.generateToken(user.getEmail());
                return new TokenResponse(newAccessToken, request.refreshToken());
            }
        }
        throw new BusinessException(ExceptionConstants.INVALID_REFRESH_TOKEN);
    }
}

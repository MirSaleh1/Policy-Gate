package com.example.policygate.repository;

import com.example.policygate.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {

    boolean existsByName(String name);

    Optional<Role> findByName(String name);
}

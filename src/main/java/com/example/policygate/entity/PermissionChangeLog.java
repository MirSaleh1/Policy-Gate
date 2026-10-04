package com.example.policygate.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "permission_change_logs")
public class PermissionChangeLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String action; // e.g. "CREATED", "UPDATED", "ASSIGNED_TO_ROLE"
    private String permissionName;
    private String roleName;
    private String changedBy; // e.g. Admin username
    private LocalDateTime changeDate;
}

package com.chibuike.usermanagement.Entity;

import com.chibuike.usermanagement.status.gender;
import com.chibuike.usermanagement.status.role;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "appusers")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;

    @Column(name = "name", nullable = false)
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private gender gender;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phoneNumber", nullable = false)
    private String phoneNumber;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "confirmPassword", nullable = false)
    private String confirmPassword;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private role role;
}

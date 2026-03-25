package com.chibuike.usermanagement.Dto;

import com.chibuike.usermanagement.status.gender;
import com.chibuike.usermanagement.status.role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class UserResponseDto {
    private UUID userId;

    private String name;

    private gender gender;

    private String email;

    private String phoneNumber;

    private role role;
}

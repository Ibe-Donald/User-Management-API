package com.chibuike.usermanagement.Dto;

import com.chibuike.usermanagement.status.gender;
import com.chibuike.usermanagement.status.role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRequestDto {

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "A gender is required")
    private gender gender;

    @NotBlank(message = "An email is required")
    @Email(message = "Provide a valid email")
    private String email;

    @Pattern(regexp = "\\d{11}", message = "A valid phone number is required")
    @NotBlank(message = "A phone number is required")
    private String phoneNumber;

    @NotNull(message = "Role is required")
    private role role;
}

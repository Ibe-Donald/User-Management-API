package com.chibuike.usermanagement.Controller;

import com.chibuike.usermanagement.Dto.UserPatchRequestDto;
import com.chibuike.usermanagement.Dto.UserRequestDto;
import com.chibuike.usermanagement.Dto.UserResponseDto;
import com.chibuike.usermanagement.Service.UserService;
import com.chibuike.usermanagement.status.role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    // Partial Update
    @PatchMapping("/{Id}")
    public UserResponseDto partialUpdate(
            @PathVariable UUID Id,
            @RequestBody @Valid UserPatchRequestDto dto
            ){
        return userService.partialUpdate(Id, dto);
    }

}

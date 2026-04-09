package com.chibuike.usermanagement.Controller;

import com.chibuike.usermanagement.Dto.UserLoginDto;
// Make sure to import your AuthResponse DTO here normally
import com.chibuike.usermanagement.Dto.LoginResponseDto;
import com.chibuike.usermanagement.Dto.UserRequestDto;
import com.chibuike.usermanagement.Dto.UserResponseDto;
import com.chibuike.usermanagement.Service.UserService;
import com.chibuike.usermanagement.Util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {


    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    public AuthController(AuthenticationManager authManager, JwtUtil jwtUtil, UserService userService) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    // Register User
    @PostMapping
    public UserResponseDto registerUsers(@RequestBody @Valid UserRequestDto dto){
        return userService.registerUser(dto);

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDto dto) { // 3. Used ResponseEntity
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            dto.getUsername(),
                            dto.getPassword()
                    )
            );

            UserDetails user = (UserDetails) authentication.getPrincipal();


            String role = user.getAuthorities().isEmpty() ?
                    "USER" : // Or whatever default fallback you want
                    user.getAuthorities().iterator().next().getAuthority();

            String token = jwtUtil.generateToken(
                    user.getUsername(),
                    role
            );

            return ResponseEntity.ok(new LoginResponseDto(token));

        } catch (BadCredentialsException e) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }
}
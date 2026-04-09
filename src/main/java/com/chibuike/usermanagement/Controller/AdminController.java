package com.chibuike.usermanagement.Controller;

import com.chibuike.usermanagement.Dto.UserRequestDto;
import com.chibuike.usermanagement.Dto.UserResponseDto;
import com.chibuike.usermanagement.Service.UserService;
import com.chibuike.usermanagement.status.role;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    // Get Users
    @GetMapping
    public Page<UserResponseDto> getUsers(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize)
    {
        return userService.fetchUsers(pageNumber,pageSize);
    }

    // Get User by role
    @GetMapping("/role")
    public Page<UserResponseDto> getUserRole (
            @RequestParam role role,
            @RequestParam int pageNumber,
            @RequestParam int pageSize){

        return userService.getByRole(role, pageNumber, pageSize);
    }

    // Updating User
    @PutMapping("/{Id}")
    public UserResponseDto updateUser(
            @PathVariable UUID Id,
            @RequestBody @Valid UserRequestDto dto
    ){
        return userService.updateUser(Id,dto);
    }

    // Get User by Id
    @GetMapping ("/{Id}")
    public UserResponseDto getUserId (@PathVariable UUID Id){
        return userService.getUserId(Id);
    }

    // Delete
    @DeleteMapping("/{Id}")
    public String deleteUser(@PathVariable UUID Id)
    {
        userService.deleteUser(Id);
        return "User with ID " + Id + " successfully deleted";
    }

}

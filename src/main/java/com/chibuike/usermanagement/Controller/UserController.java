package com.chibuike.usermanagement.Controller;

import com.chibuike.usermanagement.Dto.UserPatchRequestDto;
import com.chibuike.usermanagement.Dto.UserRequestDto;
import com.chibuike.usermanagement.Dto.UserResponseDto;
import com.chibuike.usermanagement.Service.UserService;
import com.chibuike.usermanagement.status.role;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Register User
    @PostMapping
    public UserResponseDto registerUsers(@RequestBody @Valid UserRequestDto dto){
        return userService.registerUser(dto);

    }

    // Get Users
    @GetMapping
    public Page<UserResponseDto> getUsers(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize)
    {
        return userService.fetchUsers(pageNumber,pageSize);
    }

    // Get User by Id
    @GetMapping ("/{Id}")
    public UserResponseDto getUserId (@PathVariable UUID Id){
        return userService.getUserId(Id);
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

    // Partial Update
    @PatchMapping("/{Id}")
    public UserResponseDto partialUpdate(
            @PathVariable UUID Id,
            @RequestBody @Valid UserPatchRequestDto dto
            ){
        return userService.partialUpdate(Id, dto);
    }

    // Delete
    @DeleteMapping("/{Id}")
    public String deleteUser(@PathVariable UUID Id)
    {
        userService.deleteUser(Id);
        return "User with ID " + Id + " successfully deleted";
    }

}

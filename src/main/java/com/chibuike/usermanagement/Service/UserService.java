package com.chibuike.usermanagement.Service;

import com.chibuike.usermanagement.Dto.UserPatchRequestDto;
import com.chibuike.usermanagement.Dto.UserRequestDto;
import com.chibuike.usermanagement.Dto.UserResponseDto;
import com.chibuike.usermanagement.Entity.User;
import com.chibuike.usermanagement.Exception.ResourceNotFoundException;
import com.chibuike.usermanagement.Exception.UserEmailException;
import com.chibuike.usermanagement.Repository.UserRespository;
import com.chibuike.usermanagement.status.role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRespository userRespository;

    public UserService(UserRespository userRespository) {
        this.userRespository = userRespository;
    }

    // Convert the Entity to DTO
    public UserResponseDto convertDto(User user){
        UserResponseDto dto = new UserResponseDto();

        dto.setUserId(user.getUserId());
        dto.setName(user.getName());
        dto.setGender(user.getGender());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setRole(user.getRole());

        return dto;
    }

    // Register User
    public UserResponseDto registerUser(UserRequestDto dto){

        // Checks if User already exists before registering
        if(userRespository.existsByemail(dto.getEmail())){
            throw new UserEmailException("The User with this email, " + dto.getEmail() + "exists");
        }

        User user = new User();

        user.setName(dto.getName());
        user.setGender(dto.getGender());
        user.setEmail(dto.getEmail());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setRole(dto.getRole());

        return convertDto(userRespository.save(user));
    }

    // Get all Users
    public Page<UserResponseDto> fetchUsers(int pageNumber, int pageSize){

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<User> user = userRespository.findAll(pageable);

        List<UserResponseDto> dtoList = user.stream()
                .map(this::convertDto)
                .collect(Collectors.toList());

        return new PageImpl<>(dtoList, pageable, user.getTotalElements());
    }


    // Get User by ID
    public UserResponseDto getUserId(UUID Id){
        User user = userRespository.findById(Id)
                .orElseThrow(() -> new ResourceNotFoundException("User with " + Id + "does not exists"));

        return convertDto(user);

    }

    // Get user by role

    public Page<UserResponseDto> getByRole(role role, int pageNumber, int pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<User> user = userRespository.findByRole(role, pageable);

        List<UserResponseDto> dtoList = user.stream()
                .map(this::convertDto)
                .collect(Collectors.toList());

        return new PageImpl<>(dtoList, pageable, user.getTotalElements());

    }

    // UPDATE USER
    public UserResponseDto updateUser(UUID Id, UserRequestDto dto){
        User existingUser = userRespository.findById(Id)
                .orElseThrow(() -> new ResourceNotFoundException("User with Id," + Id + " does not exist"));

        existingUser.setName(dto.getName());
        existingUser.setGender(dto.getGender());
        existingUser.setEmail(dto.getEmail());
        existingUser.setPhoneNumber(dto.getPhoneNumber());
        existingUser.setRole(dto.getRole());

        return convertDto(userRespository.save(existingUser));
    }

    //Partial update
    public UserResponseDto partialUpdate(UUID Id, UserPatchRequestDto dto){
        User existingUser = userRespository.findById(Id)
                .orElseThrow(() -> new ResourceNotFoundException("User with Id, " + Id + " does not exist"));

        if (dto.getName() != null) existingUser.setName(dto.getName());
        if (dto.getGender() != null) existingUser.setGender(dto.getGender());
        if (dto.getEmail() != null) existingUser.setEmail(dto.getEmail());
        if (dto.getPhoneNumber() != null) existingUser.setPhoneNumber(dto.getPhoneNumber());
        if (dto.getRole() != null) existingUser.setRole(dto.getRole());

        return convertDto(userRespository.save(existingUser));
    }

    // Delete User
    public void deleteUser (UUID Id){
        User existingUser = userRespository.findById(Id)
                .orElseThrow(() -> new ResourceNotFoundException("User with Id, " + Id + " does not exist"));
        userRespository.delete(existingUser);

    }
}

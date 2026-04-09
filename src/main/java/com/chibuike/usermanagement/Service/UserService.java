package com.chibuike.usermanagement.Service;

import com.chibuike.usermanagement.Dto.UserPatchRequestDto;
import com.chibuike.usermanagement.Dto.UserRequestDto;
import com.chibuike.usermanagement.Dto.UserResponseDto;
import com.chibuike.usermanagement.Entity.User;
import com.chibuike.usermanagement.Exception.PasswordException;
import com.chibuike.usermanagement.Exception.ResourceNotFoundException;
import com.chibuike.usermanagement.Exception.UserEmailException;
import com.chibuike.usermanagement.Repository.UserRepository;
import com.chibuike.usermanagement.status.role;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

    }

    // Convert the Entity to DTO
    public UserResponseDto convertDto(User user){
        UserResponseDto dto = new UserResponseDto();

        dto.setUserId(user.getUserId());
        dto.setUsername(user.getUsername());
        dto.setGender(user.getGender());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setRole(user.getRole());

        return dto;
    }

    // Register User
    public UserResponseDto registerUser(UserRequestDto dto){

        // Checks if User already exists before registering
        if(userRepository.existsByemail(dto.getEmail())){
            throw new UserEmailException("The User with this email, " + dto.getEmail() + "exists");
        }

        if(!Objects.equals(dto.getPassword(), dto.getConfirmPassword())){
            throw new PasswordException("Confirm your password");
        }

        User user = new User();

        user.setUsername(dto.getUsername());
        user.setGender(dto.getGender());
        user.setEmail(dto.getEmail());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setRole(dto.getRole());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setConfirmPassword(passwordEncoder.encode(dto.getConfirmPassword()));

        return convertDto(userRepository.save(user));
    }

    // Get all Users
    public Page<UserResponseDto> fetchUsers(int pageNumber, int pageSize){

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<User> user = userRepository.findAll(pageable);

        List<UserResponseDto> dtoList = user.stream()
                .map(this::convertDto)
                .collect(Collectors.toList());

        return new PageImpl<>(dtoList, pageable, user.getTotalElements());
    }


    // Get User by ID
    @Cacheable(value = "users", key = "#Id")
    public UserResponseDto getUserId(UUID Id){
        User user = userRepository.findById(Id)
                .orElseThrow(() -> new ResourceNotFoundException("User with " + Id + "does not exists"));

        return convertDto(user);

    }

    // Get user by role

    public Page<UserResponseDto> getByRole(role role, int pageNumber, int pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<User> user = userRepository.findByRole(role, pageable);

        List<UserResponseDto> dtoList = user.stream()
                .map(this::convertDto)
                .collect(Collectors.toList());

        return new PageImpl<>(dtoList, pageable, user.getTotalElements());

    }

    // UPDATE USER
    @CachePut(value = "users", key = "#Id")
    public UserResponseDto updateUser(UUID Id, UserRequestDto dto){
        User existingUser = userRepository.findById(Id)
                .orElseThrow(() -> new ResourceNotFoundException("User with Id," + Id + " does not exist"));

        existingUser.setUsername(dto.getUsername());
        existingUser.setGender(dto.getGender());
        existingUser.setEmail(dto.getEmail());
        existingUser.setPhoneNumber(dto.getPhoneNumber());
        existingUser.setRole(dto.getRole());

        return convertDto(userRepository.save(existingUser));
    }

    //Partial update
    @CachePut(value = "users", key = "#Id")
    public UserResponseDto partialUpdate(UUID Id, UserPatchRequestDto dto){
        User existingUser = userRepository.findById(Id)
                .orElseThrow(() -> new ResourceNotFoundException("User with Id, " + Id + " does not exist"));

        if (dto.getUsername() != null) existingUser.setUsername(dto.getUsername());
        if (dto.getGender() != null) existingUser.setGender(dto.getGender());
        if (dto.getEmail() != null) existingUser.setEmail(dto.getEmail());
        if (dto.getPhoneNumber() != null) existingUser.setPhoneNumber(dto.getPhoneNumber());
        if (dto.getRole() != null) existingUser.setRole(dto.getRole());

        return convertDto(userRepository.save(existingUser));
    }

    // Delete User
    @CacheEvict(value = "users", key = "#Id")
    public void deleteUser (UUID Id){
        User existingUser = userRepository.findById(Id)
                .orElseThrow(() -> new ResourceNotFoundException("User with Id, " + Id + " does not exist"));
        userRepository.delete(existingUser);

    }
}

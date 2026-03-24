package com.chibuike.usermanagement.Repository;

import com.chibuike.usermanagement.status.role;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.chibuike.usermanagement.Entity.*;

import java.util.UUID;

public interface UsersRespository extends JpaRepository<User, UUID> {
    boolean existsByemail(@NotBlank(message = "Email is required") String email);

    Page<User> findByRole(role role, Pageable pageable);

}

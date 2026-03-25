package com.chibuike.usermanagement.Dto;

import com.chibuike.usermanagement.status.gender;
import com.chibuike.usermanagement.status.role;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserPatchRequestDto {

    private String name;

    private gender gender;

    private String email;

    private String phoneNumber;

    private role role;
}

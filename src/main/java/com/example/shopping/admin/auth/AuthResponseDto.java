package com.example.shopping.admin.auth;

import com.example.shopping.enums.ProfileRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponseDto {
    private String name;
    private String surname;
    private ProfileRole role;
    private String jwt;
}

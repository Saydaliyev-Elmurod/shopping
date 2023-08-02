package com.example.shopping.admin.auth;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Valid
public class RegisterDto {
    @Size(message = "Name dont be empty",min = 3)
    private String name;
    @Size(message = "Surname not be empty",min = 3)
    private String surname;
    @Size(message = "Email size great than  6")
    private String email;
    @Size(message = "Password size great than  6")
    private String password;

}

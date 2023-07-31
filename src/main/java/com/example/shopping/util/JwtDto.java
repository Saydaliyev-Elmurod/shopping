package com.example.shopping.util;


import com.example.shopping.enums.ProfileRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JwtDto {
    private String mail;
    private ProfileRole role;


    private Integer id;



    public JwtDto(String email, Integer pId) {
        this.mail = email;
        this.id = pId;
    }

    public JwtDto(String email, ProfileRole profileRole) {
        this.mail = email;
        this.role = profileRole;
    }
}
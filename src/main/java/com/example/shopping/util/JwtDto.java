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

    public JwtDTO(String mail, ProfileRole role) {
        this.mail = mail;
        this.role = role;
    }
    private Integer id;

    public JwtDTO(String mail, Integer id) {
        this.mail = mail;
        this.id = id;
    }
}
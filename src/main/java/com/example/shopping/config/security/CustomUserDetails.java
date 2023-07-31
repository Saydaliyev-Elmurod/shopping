package com.example.shopping.config.security;

import com.example.shopping.enums.ProfileRole;
import com.example.shopping.profile.ProfileEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {
    private Integer id;

    public CustomUserDetails(Integer id) {
        this.id = id;
    }

    private ProfileEntity profileEntity;

    public CustomUserDetails(ProfileEntity profileEntity) {
        this.profileEntity = profileEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ProfileRole role = profileEntity.getRole();
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role.name());
        return List.of(simpleGrantedAuthority);
        //  return List.of(new SimpleGrantedAuthority(profileEntity.getRole().name()));
    }

    @Override
    public String getPassword() {
        return profileEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return profileEntity.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return profileEntity.getVisible();
    }

    public ProfileEntity getProfileEntity() {
        return profileEntity;
    }


    public Integer getId() {
        return id;
    }

}

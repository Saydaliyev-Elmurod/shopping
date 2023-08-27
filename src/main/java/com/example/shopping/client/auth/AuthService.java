package com.example.shopping.client.auth;

import com.example.shopping.enums.GeneralStatus;
import com.example.shopping.enums.ProfileRole;
import com.example.shopping.exp.AppBadRequestException;
import com.example.shopping.exp.ItemNotFoundException;
import com.example.shopping.exp.MethodNotAllowedException;
import com.example.shopping.mail.MailSenderService;
import com.example.shopping.client.profile.ProfileEntity;
import com.example.shopping.client.profile.ProfileRepository;
import com.example.shopping.util.JwtUtil;
import com.example.shopping.util.MD5Util;
import com.example.shopping.util.SpringSecurityUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService {
    private final ProfileRepository profileRepository;
    private final MailSenderService mailSenderService;

    public ResponseEntity<?> auth(RegisterDto registerDto) {
        Optional<ProfileEntity> optional = profileRepository.findByEmail(registerDto.getEmail());
        if (optional.isPresent() && optional.get().getStatus() != GeneralStatus.ROLE_REGISTER) {
            throw new ItemNotFoundException("Email already exists.");
        }
        // check email limit
        mailSenderService.checkLimit(registerDto.getEmail());

        ProfileEntity entity = null;
        if (optional.isEmpty()) {
            entity = new ProfileEntity();
        } else {
            entity = optional.get();
        }
        entity.setName(registerDto.getName());
        entity.setSurname(registerDto.getSurname());
        entity.setRole(ProfileRole.ROLE_USER);
        entity.setEmail(registerDto.getEmail());
        entity.setPassword(MD5Util.getMd5Hash(registerDto.getPassword()));
        entity.setStatus(GeneralStatus.ROLE_REGISTER);
        // send email
        mailSenderService.sendRegistrationEmail(registerDto.getEmail());
//        mailSenderService.sendRegistrationEmailMime(dto.getEmail());
        // save
        profileRepository.save(entity);
        String s = "Verification link was send to email: " + registerDto.getEmail();
        return ResponseEntity.ok(s);
    }

    public Object emailVerification(String jwt) {
        String email = JwtUtil.decodeEmailVerification(jwt);
        Optional<ProfileEntity> optional = profileRepository.findByEmail(email);
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("Email not found.");
        }
        ProfileEntity entity = optional.get();
        if (!entity.getStatus().equals(GeneralStatus.ROLE_REGISTER)) {
            throw new AppBadRequestException("Wrong status");
        }
        entity.setStatus(GeneralStatus.ROLE_ACTIVE);
        profileRepository.save(entity);
        return new String("Registration Done");
    }

    public AuthResponseDto login(AuthDto dto) {
        Optional<ProfileEntity> optional = profileRepository.findByEmailAndPasswordAndVisible(
                dto.getEmail(),
                MD5Util.getMd5Hash(dto.getPassword()),
                true);
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("Email or password incorrect");
        }
        ProfileEntity entity = optional.get();
        if (!entity.getStatus().equals(GeneralStatus.ROLE_ACTIVE))
            throw new MethodNotAllowedException("Wrong status,Method not allowed");
        AuthResponseDto responseDTO = new AuthResponseDto();
        responseDTO.setName(entity.getName());
        responseDTO.setSurname(entity.getSurname());
        responseDTO.setRole(entity.getRole());
        responseDTO.setJwt(JwtUtil.encode(entity.getEmail(), entity.getRole()));
        return responseDTO;
    }

    public ResponseEntity updateAdmin(AuthDto authDto) {
        Integer adminId = SpringSecurityUtil.getProfileId();
        Integer rs = profileRepository.updatePasswordAndEmail(adminId, authDto.getPassword(), authDto.getEmail());
        return ResponseEntity.ok(rs==1);
    }
}

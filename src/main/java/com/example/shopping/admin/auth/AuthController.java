package com.example.shopping.admin.auth;

import com.example.shopping.payload.JwtResponse;
import com.example.shopping.payload.LoginRequest;
import com.example.shopping.payload.MessageResponse;
import com.example.shopping.payload.SignupRequest;
import com.example.shopping.role.ERole;
import com.example.shopping.role.Role;
import com.example.shopping.role.RoleRepository;
import com.example.shopping.user.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("public/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody RegisterDto dto) {
      return authService.auth(dto);
    }

    @GetMapping("verification/{jwt}")
    public ResponseEntity<?> emailVerification(@PathVariable("jwt") String jwt) {
        return ResponseEntity.ok(authService.emailVerification(jwt));
    }
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthDto dto) {
        return ResponseEntity.ok(authService.login(dto));
    }
}

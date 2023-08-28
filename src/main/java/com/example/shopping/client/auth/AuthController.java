package com.example.shopping.client.auth;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("auth")
@CrossOrigin("http://localhost:5173")
public class AuthController {
    private static Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    @PostMapping("sign-up")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody RegisterDto dto) {
        return authService.auth(dto);
    }

    @GetMapping("verification/{jwt}")
    public ResponseEntity<?> emailVerification(@PathVariable("jwt") String jwt) {
        return ResponseEntity.ok(authService.emailVerification(jwt));
    }

    @CrossOrigin()
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthDto dto) {
        logger.info(dto.getPassword().toString());
        logger.info(dto.getEmail().toString());
        return ResponseEntity.ok(authService.login(dto));
    }
}

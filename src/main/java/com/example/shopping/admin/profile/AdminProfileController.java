package com.example.shopping.admin.profile;

import com.example.shopping.client.auth.AuthDto;
import com.example.shopping.client.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("admin/profile")
@RestController
@SecurityRequirement(name = "online shop ")
@AllArgsConstructor
@Tag(name = "Admin  profile ", description = "admin update profile password and email")
public class AdminProfileController {
    private final AuthService authService;

    @Operation(description = "admin change password and email")
    @ApiResponse(responseCode = "200")
    @PostMapping("update")
    public ResponseEntity<?> update(@RequestBody AuthDto authDto) {
        return authService.updateAdmin(authDto);
    }
}

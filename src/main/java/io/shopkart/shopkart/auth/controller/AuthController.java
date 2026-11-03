package io.shopkart.shopkart.auth.controller;

import io.shopkart.shopkart.auth.dto.*;
import io.shopkart.shopkart.auth.service.AuthService;
import io.shopkart.shopkart.auth.service.JwtService;
import io.shopkart.shopkart.common.dto.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;

    public AuthController(AuthService authService, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterResponseDto>> register(@Valid @RequestBody RegisterRequestDto registerRequestDto) {
        RegisterResponseDto responseDto = authService.register(registerRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(responseDto));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDto>> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {

        Authentication authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(
                loginRequestDto.email(),
                loginRequestDto.password()
        );

        Authentication authentication = authenticationManager.authenticate(authenticationRequest);
        String token = jwtService.generateToken(authentication);

        return ResponseEntity.ok(ApiResponse.success(new LoginResponseDto(token)));

    }

    @PostMapping("/forgot-password")
    public void forgotPassword() {

    }

    @PostMapping("/reset-password")
    public void resetPassword() {

    }

}

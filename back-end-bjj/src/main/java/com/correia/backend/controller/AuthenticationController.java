package com.correia.backend.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.correia.backend.controller.dto.LoginRequestDTO;
import com.correia.backend.controller.dto.RegisterRequestDTO;
import com.correia.backend.controller.dto.UserLoginDto;
import com.correia.backend.model.User;
import com.correia.backend.servico.AuthenticationService;
import com.correia.backend.servico.exception.AppException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public User register(@Valid @RequestBody RegisterRequestDTO user) {
        if (user.email() == null || user.email().isEmpty() ||
            user.password() == null || user.password().isEmpty()) {
            throw new AppException("All fields are required.", HttpStatus.BAD_REQUEST);
        }

        return authenticationService.register(user.name(), user.email(), user.password());
    }

    @PostMapping("/login")
    public UserLoginDto login(@Valid @RequestBody LoginRequestDTO user) {
        UserLoginDto userLoginDto = authenticationService.login(user.email(), user.password());

        if (userLoginDto.getUser() == null) {
            throw new AppException("Invalid credentials.", HttpStatus.NOT_FOUND);
        }

        return userLoginDto;
    }

    @PostMapping("/refresh")
    public UserLoginDto refresh(@RequestBody String refreshToken) {

        if (refreshToken == null || refreshToken.isEmpty()) {
            throw new AppException("Refresh token required.", HttpStatus.BAD_REQUEST);
        }

        UserLoginDto dto = authenticationService.refreshToken(refreshToken);

        if (dto.getJwt() == null || dto.getJwt().isEmpty()) {
            throw new AppException("Invalid refresh token.", HttpStatus.UNAUTHORIZED);
        }

        return dto;
    }
    
    @PostMapping("/logout")
    public Map<String, String> logout(@RequestHeader("Authorization") String header) {

        if (header == null || !header.startsWith("Bearer ")) {
            throw new AppException("Token not found", HttpStatus.BAD_REQUEST);
        }

        String token = header.substring(7);

        authenticationService.logout(token);

        return Map.of("message", "Logged out successfully");
    }

}

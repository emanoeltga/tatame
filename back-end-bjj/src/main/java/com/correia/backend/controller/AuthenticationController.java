package com.correia.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.correia.backend.controller.dto.RegisterRequestDTO;
import com.correia.backend.controller.dto.UserDto;
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
        if (user.email() == null || user.email().isEmpty() || user.password() == null || user.password().isEmpty()) {
            throw new AppException("All fields are required.", HttpStatus.BAD_REQUEST);
        }

        return authenticationService.register(user.name(),user.email(), user.password());
    }

    @PostMapping("/login")
    public UserLoginDto login(@Valid @RequestBody UserDto user) {
        UserLoginDto userLoginDto = authenticationService.login(user.getEmail(), user.getPassword());

        if (userLoginDto.getUser() == null) {
            throw new AppException("Invalid credentials.", HttpStatus.NOT_FOUND);
        }

        return userLoginDto;
    }
}
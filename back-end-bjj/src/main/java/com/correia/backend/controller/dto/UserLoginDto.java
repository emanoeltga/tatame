package com.correia.backend.controller.dto;

import com.correia.backend.model.User;

import lombok.Getter;

@Getter
public class UserLoginDto {

    private Long id;
    private User user;
    private String jwt;

    public UserLoginDto() {
        super();
    }

    public UserLoginDto(Long id, User user, String jwt) {
        this.id = id;
        this.user = user;
        this.jwt = jwt;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

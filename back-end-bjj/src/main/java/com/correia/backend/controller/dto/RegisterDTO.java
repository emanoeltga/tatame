package com.correia.backend.controller.dto;

import java.util.Collection;

import com.correia.backend.model.UserRole;


public record RegisterDTO(String login, String password, Collection<UserRole> role) {

}

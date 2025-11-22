package com.correia.bjj.controller.dto;

import java.util.Collection;

import com.correia.bjj.model.Role;

public record RegisterDTO(String login, String password, Collection<Role> role) {

}

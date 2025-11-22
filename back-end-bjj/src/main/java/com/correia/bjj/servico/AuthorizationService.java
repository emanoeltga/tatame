package com.correia.bjj.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.correia.bjj.model.UserJWT;

public class AuthorizationService implements UserDetailsService {

    @Autowired
    UsersService usersService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	UserJWT user = new UserJWT(usersService.buscaUser(username)); 
    	return user;
    }
}

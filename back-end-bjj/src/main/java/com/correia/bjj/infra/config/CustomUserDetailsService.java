package com.correia.bjj.infra.config;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.correia.bjj.model.Users;
import com.correia.bjj.repository.UsersRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UsersRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = this.repository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        // as Roles esta sendo passado vazio, depois fazer consulta passando as Roles deste usuario.
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }
}

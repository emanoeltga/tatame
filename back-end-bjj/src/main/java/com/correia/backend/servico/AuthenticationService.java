package com.correia.backend.servico;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;

import com.correia.backend.controller.dto.UserLoginDto;
import com.correia.backend.infra.config.TokenService;
import com.correia.backend.model.TokenBlacklist;
import com.correia.backend.model.User;
import com.correia.backend.model.UserRole;
import com.correia.backend.repository.RoleRepository;
import com.correia.backend.repository.TokenBlacklistRepository;
import com.correia.backend.repository.UsersRepository;

import jakarta.transaction.Transactional;
@Service
@Transactional
public class AuthenticationService {

    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private RoleRepository userRoleRepository;
    
    @Autowired
    private TokenBlacklistRepository blacklistRepository;
    
    @Autowired
    private JwtDecoder jwtDecoder;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    public User register(String name,String email, String password) {
        String encodedPassword = passwordEncoder.encode(password);

        UserRole userRole = userRoleRepository.findByAuthority("USER").get();
        Set<UserRole> authorities = new HashSet<>();

        authorities.add(userRole);

        return userRepository.save(new User(name, email, encodedPassword, authorities));
    }

    public UserLoginDto login(String email, String password) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            String token = tokenService.generateJwt(auth);

            User user = userRepository.findByEmail(email).orElse(null);
            if (user != null) {
                return new UserLoginDto(user.getId(), user, token);
            } else {
                return new UserLoginDto(null, null, "");
            }

        } catch (AuthenticationException e) {
            return new UserLoginDto(null, null, "");
        }
    }
    public UserLoginDto refreshToken(String refreshToken) {
        try {
            String email = tokenService.extractUsername(refreshToken);

            if (email == null) {
                return new UserLoginDto(null, null, "");
            }

            User user = userRepository.findByEmail(email).orElse(null);

            if (user == null) {
                return new UserLoginDto(null, null, "");
            }

            // Gera um novo JWT (access token)
            String newToken = tokenService.generateJwtFromUser(user);

            return new UserLoginDto(user.getId(), user, newToken);

        } catch (Exception e) {
            return new UserLoginDto(null, null, "");
        }
    }
    
    public void logout(String token) {

        try {
        	
            Jwt decoded = jwtDecoder.decode(token);

            Instant exp = decoded.getExpiresAt();

            // salva o token na blacklist
            TokenBlacklist blacklist = new TokenBlacklist(token, exp);
            blacklistRepository.save(blacklist);

        } catch (Exception e) {
            // token inválido → só ignora
        	
        }
    }

    
}

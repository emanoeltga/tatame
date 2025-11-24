package com.correia.backend.infra.config;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.correia.backend.model.User;

@Service
public class TokenService {

	@Autowired
	private JwtEncoder jwtEncoder;

	@Autowired
	private JwtDecoder jwtDecoder;

	// =============================
	// ACCESS TOKEN - 15min
	// =============================
	public String generateJwt(Authentication auth) {

		Instant now = Instant.now();

		List<String> roles = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

		JwtClaimsSet claims = JwtClaimsSet.builder().issuer("self").issuedAt(now)
				.expiresAt(now.plus(15, ChronoUnit.MINUTES)).subject(auth.getName()).claim("roles", roles)
				.claim("type", "access").build();

		return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
	}

	// =============================
	// REFRESH TOKEN - 7 dias
	// =============================
	public String generateRefreshToken(Authentication auth) {

		Instant now = Instant.now();

		JwtClaimsSet claims = JwtClaimsSet.builder().issuer("self").issuedAt(now)
				.expiresAt(now.plus(7, ChronoUnit.DAYS)).subject(auth.getName()).claim("type", "refresh").build();

		return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
	}

	// =============================
	// Extrair username do token
	// =============================
	public String extractUsername(String token) {
		Jwt decoded = jwtDecoder.decode(token);
		return decoded.getSubject();
	}

	// =============================
	// Regenerar JWT a partir do usuário (para refresh)
	// =============================
	public String generateJwtFromUser(User user) {

		Instant now = Instant.now();

		List<String> roles = user.getAuthorities().stream().map(r -> "ROLE_" + r.getAuthority()).toList();

		JwtClaimsSet claims = JwtClaimsSet.builder().issuer("self").issuedAt(now)
				.expiresAt(now.plus(15, ChronoUnit.MINUTES)).subject(user.getEmail()).claim("roles", roles)
				.claim("type", "access").build();

		return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
	}
}

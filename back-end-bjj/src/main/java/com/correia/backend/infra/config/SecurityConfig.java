package com.correia.backend.infra.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.DefaultSecurityFilterChain;

import com.correia.backend.utils.RSAKeyProperties;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Classe contendo as chaves RSA usadas para assinar/validar tokens JWT
    private final RSAKeyProperties keys;

    public SecurityConfig(RSAKeyProperties keys){
        this.keys = keys;
    }

    // Bean responsável por criptografar senhas com BCrypt
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // Bean que define como o Spring vai autenticar usuários
    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService){
        DaoAuthenticationProvider daoAuthenticationProviderProvider = new DaoAuthenticationProvider();

        // Quem fornece os dados do usuário (busca no banco)
        daoAuthenticationProviderProvider.setUserDetailsService(userDetailsService);

        // Qual encoder será usado para validar senha
        daoAuthenticationProviderProvider.setPasswordEncoder(passwordEncoder());

        // AuthenticationManager usando esse Provider
        return new ProviderManager(daoAuthenticationProviderProvider);
    }

    // Configuração principal da segurança
    @Bean
    public DefaultSecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Desabilita CSRF (para APIs stateless)
                .csrf(AbstractHttpConfigurer::disable)

                // Configura regras de autorização por endpoint
                .authorizeHttpRequests(auth -> {
                	
                	// Libera Swagger/OpenAPI
                	auth.requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll();
                	
                    // Endpoints públicos para login e registro
                    auth.requestMatchers("/api/v1/auth/**").permitAll();

                    // Endpoints administrativos
                    auth.requestMatchers("/api/v1/admin/**").hasRole("ADMIN");

                    // Endpoints públicos de usuário
                    auth.requestMatchers("/api/v1/user/**").permitAll();

                    // Produtos disponíveis sem autenticação
                    auth.requestMatchers("/api/v1/products/all").permitAll();
                    auth.requestMatchers("/api/v1/products/{productId}").permitAll();

                    // Apenas ADMIN pode cadastrar produtos
                    auth.requestMatchers("/api/v1/products/add").hasRole("ADMIN");

                    // Carrinho, pagamentos e pedidos públicos
                    auth.requestMatchers("/api/v1/cart/**").permitAll();
                    auth.requestMatchers("/api/v1/payments/**").permitAll();
                    auth.requestMatchers("/api/v1/orders/**").permitAll();

                    // Qualquer outro endpoint exige autenticação
                    auth.anyRequest().authenticated();
                })

                // Ativa servidor OAuth2 Resource com validação JWT
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
                )

                // API stateless — sem sessões
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        return http.build();
    }

    // Decodificador de JWT usando a chave pública (para validar tokens)
    @Bean
    public JwtDecoder jwtDecoder(){
        return NimbusJwtDecoder.withPublicKey(keys.getPublicKey()).build();
    }

    // Codificador JWT (para gerar tokens), usando chave privada
    @Bean
    public JwtEncoder jwtEncoder(){
        JWK jwk = new RSAKey.Builder(keys.getPublicKey())
                .privateKey(keys.getPrivateKey())
                .build();

        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));

        return new NimbusJwtEncoder(jwks);
    }

    // Converte claims do JWT em authorities do Spring Security
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter(){
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

        // Nome do claim onde estão os papéis do usuário
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("roles");

        // Prefixo padrão de roles no Spring Security
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();

        // Aplica o conversor criado acima
        jwtConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

        return jwtConverter;
    }
}
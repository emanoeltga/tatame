package com.correia.backend.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;


@Getter
@Data
@Entity
@Table(name = "users")
public class User implements UserDetails {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", unique = true)
    private Long id;
	
    @NotEmpty(message = "Email é requirido.")
    @Email(message = "E-mail valido é requirido.")
    private String email;
   
    @NotEmpty(message = "nome é requirido.")
    @Size(min = 3, message = "A senha deve ter pelo menos 3 caracteres.")
    private String name;

    @NotEmpty(message = "Password é requirido.")
    @Size(min = 8, message = "A senha deve ter pelo menos 8 caracteres.")
    private String password;

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(
            name="user_role_junction",
            joinColumns = {@JoinColumn(name="user_id")},
            inverseJoinColumns = {@JoinColumn(name="role_id")}
    )
    private Set<UserRole> authorities;
    
    private boolean ativo;// = true;          // controla se está ativo
    private boolean bloqueado;// = false;     // controla se está bloqueado
    private boolean contaExpirada;// = false; // controla se a conta expirou
    private boolean credencialExpirada;// = false; // controla se a senha expirou
    

    public User() {
        super();
        authorities = new HashSet<>();
    }

    public User(String name,String email, String password, Set<UserRole> authorities) {
        super();
        this.name=name;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.ativo=true;
    }
    

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public void setAuthorities(Set<UserRole> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !contaExpirada;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !bloqueado;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !credencialExpirada;
    }

    @Override
    public boolean isEnabled() {
        return ativo;
    }
    
}


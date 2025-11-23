package com.correia.backend.model;

import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_roles")
public class UserRole implements GrantedAuthority {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1349000651609775721L;
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="role_id")
    private Long id;
	
	@Column(unique = true, nullable = false, length = 50)
    private String authority; // ex: ADMIN, USER
    
    public UserRole(){
        super();
    }
    public UserRole(String authority){
        this.authority = authority;
    }
    public UserRole(Long id, String authority){
        this.id = id;
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }

    public void setAuthority(String authority){
        this.authority = authority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
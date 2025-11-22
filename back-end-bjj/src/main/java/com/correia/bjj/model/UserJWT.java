package com.correia.bjj.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserJWT implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long id;
	private Users user;
	private Collection<Role> roles;
	
	public UserJWT(Users users) {
		this.user=users;
		this.roles=user.getRoles();
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return roles.stream()
				.map(role-> new SimpleGrantedAuthority(role.getRole()))
				.collect(Collectors.toCollection(ArrayList::new));
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getName();
	}
	//Esta expirado? retonar True ou False, o padrão é False;
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
//Credenciais esta bloqueada, o padrão é False;
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}
	
//credenciais esta expirada?, o padrão é False;
	
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		if (user.getAtivo() == 1) {
			return true;
		} else
		return false;
	}

}

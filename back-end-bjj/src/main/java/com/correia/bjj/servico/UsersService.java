package com.correia.bjj.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.correia.bjj.model.Users;
import com.correia.bjj.repository.UsersRepository;

@Service
public class UsersService {

	@Autowired
	private UsersRepository usersRepository;
	/*@Autowired
	private RoleRepository roleRepository;*/
	
	public Users buscaUser(String login) {
		//vericar se o JPA vai trazer as Roles
		return usersRepository.findByEmail(login).orElseThrow();
	}
	
}

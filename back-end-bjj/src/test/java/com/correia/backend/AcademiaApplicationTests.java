package com.correia.backend;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.correia.backend.model.User;
import com.correia.backend.model.UserRole;
import com.correia.backend.repository.RoleRepository;
import com.correia.backend.repository.UsersRepository;

@SpringBootTest
class AcademiaApplicationTests {

	@Autowired
	private UsersRepository usersRepository;
	
    @Autowired
    private RoleRepository userRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
	
	private User user;
	@Test
    void testeSimples() {
        System.out.println("Rodou o teste!");
    }
	@Test
	void salvarUsuario() {
		
		UserRole roles = new UserRole();
		roles.setAuthority("ADMIN");
		userRoleRepository.save(roles);
		UserRole roles1 = new UserRole();
		roles1.setAuthority("USER");
		userRoleRepository.save(roles1);
		
		UserRole userRole = userRoleRepository.findByAuthority("USER").get();
        Set<UserRole> authorities = new HashSet<>();

        authorities.add(userRole);
        user=new User("Emanoel","emanoeltga@gmail.com", passwordEncoder.encode("banana123"), authorities);
        //user.setName("Emanoel");
        usersRepository.save(user);
	}

}

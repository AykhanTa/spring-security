package com.example.spring_security;

import com.example.spring_security.entities.Role;
import com.example.spring_security.entities.User;
import com.example.spring_security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@RequiredArgsConstructor
public class SpringSecurityApplication implements CommandLineRunner {

	private final UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User adminAcc=userRepository.findByRole(Role.ADMIN);
		if(adminAcc==null){
			adminAcc=new User();

			adminAcc.setRole(Role.ADMIN);
			adminAcc.setFirstName("Admin");
			adminAcc.setLastName("Admin");
			adminAcc.setEmail("admin@example.com");
			adminAcc.setPassword(new BCryptPasswordEncoder().encode("admin"));
			userRepository.save(adminAcc);

		}
	}
}

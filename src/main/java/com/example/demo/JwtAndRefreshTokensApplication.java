package com.example.demo;

import com.example.demo.model.AppUser;
import com.example.demo.model.Role;
import com.example.demo.service.AppUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class JwtAndRefreshTokensApplication {

	public static void main(String[] args) {

		SpringApplication.run(JwtAndRefreshTokensApplication.class, args);
	}
	@Bean
	PasswordEncoder passwordEncoder(){
		return  new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run(AppUserService appUserService) {
		return args -> {
			appUserService.saveRole(new Role(null, "ROLE_USER"));
			appUserService.saveRole(new Role(null, "ROLE_MANAGER"));
			appUserService.saveRole(new Role(null, "ROLE_ADMIN"));
			appUserService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));


			appUserService.saveAppUser(new AppUser(null,"john Travelta","hendry@gmail.com","1234",new ArrayList<>()));
			appUserService.saveAppUser(new AppUser(null,"john mwamburi","mwamburi@gmail.com","1234",new ArrayList<>()));
			appUserService.saveAppUser(new AppUser(null,"john bones","bones@gmail.com","1234",new ArrayList<>()));
			appUserService.saveAppUser(new AppUser(null,"john bones","mkandoe@gmail.com","1234",new ArrayList<>()));

			appUserService.addRoleToAppUser("hendry@gmail.com","ROLE_USER");
			appUserService.addRoleToAppUser("mwamburi@gmail.com","ROLE_MANAGER");
			appUserService.addRoleToAppUser("bones@gmail.com","ROLE_ADMIN");
			appUserService.addRoleToAppUser("mkandoe@gmail.com","ROLE_SUPER_ADMIN");

		};
	}

}

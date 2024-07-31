package com.app;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.app.domain.user.ForoUser;
import com.app.domain.user.Person;
import com.app.domain.user.Role;
import com.app.repositories.PersonRepositoryImp;
import com.app.repositories.UserRepositoryImp;

@SpringBootApplication
public class ProyectoForoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProyectoForoApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UserRepositoryImp userRepository, PersonRepositoryImp personRepositoryImp){
		return args -> {
			Role userRole = Role.builder().name("USER").build();
			Role adminRole = Role.builder().name("ADMIN").build();

			Person joseP = Person.builder()
													.firstName("Jose")
													.lastName("Lopez")
													.email("mairiciohualpa@gmail.com")
													.dateOfBirthDay(LocalDate.of(2000,8,28))
													.build();
			Person valeryP = Person.builder()
													.firstName("Valery")
													.lastName("Quintanilla")
													.email("valeryq@gmail.com")
													.dateOfBirthDay(LocalDate.of(2002,6,15))
													.build();
			personRepositoryImp.saveAll(List.of(joseP,valeryP));

			ForoUser maurpz = ForoUser.builder()
																		.username("Maurpz")
																		.password("12345")
																		.person(joseP)
																		.isEnabled(true)
																		.accountNoExpired(true)
																		.credentialNoExpired(true)
																		.accountNoLocked(true)
																		.roles(Set.of(adminRole))
																		.build();
			ForoUser valery = ForoUser.builder()
																		.username("valery")
																		.password("123456")
																		.person(valeryP)
																		.isEnabled(true)
																		.accountNoExpired(true)
																		.credentialNoExpired(true)
																		.accountNoLocked(true)
																		.roles(Set.of(userRole))
																		.build();

																		
			userRepository.saveAll(List.of(maurpz,valery));
			//ptmr otra rama raaa
		};

	}

}

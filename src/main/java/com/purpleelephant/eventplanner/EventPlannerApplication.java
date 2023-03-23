package com.purpleelephant.eventplanner;

import com.purpleelephant.eventplanner.organization.Organization;
import com.purpleelephant.eventplanner.organization.OrganizationRepository;
import com.purpleelephant.eventplanner.person.Person;
import com.purpleelephant.eventplanner.person.PersonRepository;
import com.purpleelephant.eventplanner.security.user.Role;
import com.purpleelephant.eventplanner.security.user.User;
import com.purpleelephant.eventplanner.security.user.UserRepository;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@OpenAPIDefinition(
    info = @io.swagger.v3.oas.annotations.info.Info(title = "Event Planner API", version = "v1"),
    servers = @io.swagger.v3.oas.annotations.servers.Server(url = "http://localhost:8080")
)

@SecurityScheme(name = "BearerJWT", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
public class EventPlannerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventPlannerApplication.class, args);
    }

    @Bean
    CommandLineRunner run(PasswordEncoder passwordEncoder, UserRepository userRepository, OrganizationRepository organizationRepository, PersonRepository personRepository) {
        return args -> {
            userRepository.save(new User(null, "test", passwordEncoder.encode("test"), Role.USER));

            organizationRepository.save(new Organization(null, true, "Vezetes", null));
            organizationRepository.save(new Organization(null, false, "Al-osztaly 1", new Organization(1, null, null, null)));
            organizationRepository.save(new Organization(null, false, "Al-osztaly 2", new Organization(1, null, null, null)));
            organizationRepository.save(new Organization(null, false, "Takaritok", new Organization(2, null, null, null)));

            personRepository.save(new Person(null, "Giga", "Chad", "Top CEO", new Organization(1, null, null, null)));
            personRepository.save(new Person(null, "Komoly", "Janos", "Nem komoly", new Organization(1, null, null, null)));
            personRepository.save(new Person(null, "Kis", "Juliska", "Kis titkarno", new Organization(2, null, null, null)));
            personRepository.save(new Person(null, "Nagy", "Marcsi", "Nagy titkarno", new Organization(3, null, null, null)));
            personRepository.save(new Person(null, "Takker", "Neni1", "Top level takaritoneni", new Organization(4, null, null, null)));
            personRepository.save(new Person(null, "Takker", "Neni2", "Gyakornok takker neni", new Organization(4, null, null, null)));
        };
    }

}

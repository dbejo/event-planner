package com.purpleelephant.eventplanner;

import com.purpleelephant.eventplanner.event.Event;
import com.purpleelephant.eventplanner.event.EventRepository;
import com.purpleelephant.eventplanner.organization.Organization;
import com.purpleelephant.eventplanner.organization.OrganizationRepository;
import com.purpleelephant.eventplanner.person.Person;
import com.purpleelephant.eventplanner.person.PersonRepository;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(title = "Event Planner API", version = "v1"),
        servers = @io.swagger.v3.oas.annotations.servers.Server(url = "http://localhost:8080")
)
public class EventPlannerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventPlannerApplication.class, args);
    }

    @Bean
    CommandLineRunner run(OrganizationRepository organizationRepository, PersonRepository personRepository, EventRepository eventRepository) {
        return args -> {
            organizationRepository.save(new Organization(null, true, "Vezetes", true, "Putypurutty utca 2", null, null));
            organizationRepository.save(new Organization(null, false, "Al-osztaly 1", true, "Putypurutty utca 2", null, new Organization(1, null, null, null, null, null, null)));
            organizationRepository.save(new Organization(null, false, "Al-osztaly 2", true, "Putypurutty utca 2", null, new Organization(1, null, null, null, null, null, null)));
            organizationRepository.save(new Organization(null, false, "Takaritok", true, "Putypurutty utca 2", null, new Organization(2, null, null, null, null, null, null)));

            personRepository.save(new Person(null, "Giga", "Chad", "Top CEO", "email1@email.com", true, List.of(new Organization(1, null, null, null, null, null, null)), null));
            personRepository.save(new Person(null, "Komoly", "Janos", "Nem komoly", "email2@email.com", true, List.of(new Organization(1, null, null, null, null, null, null)), null));
            personRepository.save(new Person(null, "Kis", "Juliska", "Kis titkarno", "email3@email.com", true, List.of(new Organization(2, null, null, null, null, null, null)), null));
            personRepository.save(new Person(null, "Nagy", "Marcsi", "Nagy titkarno", "email4@email.com", true, List.of(new Organization(3, null, null, null, null, null, null)), null));
            personRepository.save(new Person(null, "Takker", "Neni1", "Top level takaritoneni", "email5@email.com", true, List.of(new Organization(4, null, null, null, null, null, null)), null));
            personRepository.save(new Person(null, "Takker", "Neni2", "Gyakornok takker neni", "email6@email.com", true, List.of(new Organization(4, null, null, null, null, null, null)), null));

            eventRepository.save(new Event(null, "Kajalas a fonivel", "Lesz sok kaja gyertek", "McDonalds", Date.valueOf(LocalDate.now()), true,
                    List.of(new Person(1, null, null, null, null, null, null, null),
                            new Person(2, null, null, null, null, null, null, null),
                            new Person(4, null, null, null, null, null, null, null))));
            eventRepository.save(new Event(null, "Takker buli", "Koszosan gyertek", "3. emelet", Date.valueOf(LocalDate.now()), true,
                    List.of(new Person(5, null, null, null, null, null, null, null),
                            new Person(6, null, null, null, null, null, null, null))));
        };
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:4200"));
        corsConfiguration.setAllowedHeaders(List.of("Origin", "Access-Control-Allow-Origin", "Content-Type", "Accept", "Authorization", "Origin, Accept", "X-Requested-With", "Access-Control-Request-Method", "Access-Control-Request-Headers"));
        corsConfiguration.setExposedHeaders(List.of("Origin", "Content-Type", "Accept", "Authorization", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }

}

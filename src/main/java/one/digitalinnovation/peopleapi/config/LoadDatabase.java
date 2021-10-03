package one.digitalinnovation.peopleapi.config;

import one.digitalinnovation.peopleapi.dto.request.PersonDTO;
import one.digitalinnovation.peopleapi.dto.request.PhoneDTO;
import one.digitalinnovation.peopleapi.enums.PhoneType;
import one.digitalinnovation.peopleapi.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class LoadDatabase {

    private static final Logger logger = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(final PersonService personService) {
        return args -> {
            PhoneDTO phone1 = PhoneDTO.builder()
                    .number("1199999-9999")
                    .type(PhoneType.MOBILE)
                    .build();
            PersonDTO person1 = PersonDTO.builder()
                    .firstName("Sarah")
                    .lastName("Kerrigan")
                    .cpf("369.333.878-79")
                    .birthDate("04-04-1996")
                    .phones(Collections.singletonList(phone1))
                    .build();


            PhoneDTO phone2 = PhoneDTO.builder()
                    .number("1622222-1111")
                    .type(PhoneType.HOME)
                    .build();
            PersonDTO person2 = PersonDTO.builder()
                    .firstName("James")
                    .lastName("Raynor")
                    .cpf("274.026.010-56")
                    .birthDate("09-06-1995")
                    .phones(Collections.singletonList(phone2))
                    .build();

            personService.createPerson(person1);
            logger.info("Person created: " + person1.getFirstName() + " " + person1.getLastName());
            personService.createPerson(person2);
            logger.info("Person created: " + person2.getFirstName() + " " + person2.getLastName());
        };
    }
}

package one.digitalinnovation.peopleapi.mapper;

import one.digitalinnovation.peopleapi.dto.request.PersonDTO;
import one.digitalinnovation.peopleapi.dto.request.PhoneDTO;
import one.digitalinnovation.peopleapi.entity.Person;
import one.digitalinnovation.peopleapi.entity.Phone;
import one.digitalinnovation.peopleapi.utils.PersonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PersonMapperTest {

    @Autowired
    private PersonMapper personMapper;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Test
    void testGivenPersonDTOThenReturnPersonEntity() {
        PersonDTO personDTO = PersonUtils.createFakeDTO();
        Person person = personMapper.toModel(personDTO);

        assertEquals(person.getFirstName(), personDTO.getFirstName());
        assertEquals(person.getLastName(), personDTO.getLastName());
        assertEquals(person.getCpf(), personDTO.getCpf());
        assertEquals(getLocalDateAsString(person.getBirthDate()), personDTO.getBirthDate());

        Phone phone = person.getPhones().get(0);
        PhoneDTO phoneDTO = personDTO.getPhones().get(0);

        assertEquals(phone.getNumber(), phoneDTO.getNumber());
        assertEquals(phone.getType(), phoneDTO.getType());
    }

    @Test
    void testGivenPersonEntityThenReturnPersonDTO() {
        Person person = PersonUtils.createFakeEntity();
        PersonDTO personDTO = personMapper.toDTO(person);

        assertEquals(person.getFirstName(), personDTO.getFirstName());
        assertEquals(person.getLastName(), personDTO.getLastName());
        assertEquals(person.getCpf(), personDTO.getCpf());
        assertEquals(getLocalDateAsString(person.getBirthDate()), personDTO.getBirthDate());

        Phone phone = person.getPhones().get(0);
        PhoneDTO phoneDTO = personDTO.getPhones().get(0);

        assertEquals(phone.getNumber(), phoneDTO.getNumber());
        assertEquals(phone.getType(), phoneDTO.getType());
    }

    private String getLocalDateAsString(LocalDate date) {
        return date == null? null : date.format(dateFormatter);
    }
}

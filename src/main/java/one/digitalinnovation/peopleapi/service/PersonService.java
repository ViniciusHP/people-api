package one.digitalinnovation.peopleapi.service;

import lombok.AllArgsConstructor;
import one.digitalinnovation.peopleapi.dto.request.PersonDTO;
import one.digitalinnovation.peopleapi.dto.response.MessageResponseDTO;
import one.digitalinnovation.peopleapi.entity.Person;
import one.digitalinnovation.peopleapi.exception.PersonNotFoundException;
import one.digitalinnovation.peopleapi.mapper.PersonMapper;
import one.digitalinnovation.peopleapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonService {

    private PersonRepository personRepository;
    private MessageSource messageSource;

    private final PersonMapper personMapper = PersonMapper.INSTANCE;

    public MessageResponseDTO createPerson(PersonDTO personDTO) {
        Person personToSave = personMapper.toModel(personDTO);
        Person savedPerson = personRepository.save(personToSave);
        return createMessageResponse(savedPerson.getId(), "person.created");
    }

    public List<PersonDTO> listAll() {
        List<Person> allPeople = personRepository.findAll();
        return allPeople.stream()
                .map(personMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PersonDTO findById(Long id) throws PersonNotFoundException {
        Person person = verifyIfExists(id);
        return personMapper.toDTO(person);
    }

    public void delete(Long id) throws PersonNotFoundException {
        verifyIfExists(id);
        personRepository.deleteById(id);
    }

    public MessageResponseDTO updateById(Long id, PersonDTO personDTO) throws PersonNotFoundException {
        verifyIfExists(id);

        Person personToUpdate = personMapper.toModel(personDTO);
        Person updatePerson = personRepository.save(personToUpdate);
        return createMessageResponse(updatePerson.getId(), "person.updated");
    }

    private Person verifyIfExists(Long id) throws PersonNotFoundException {
        return personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
    }

    private MessageResponseDTO createMessageResponse(Long id, String messageCode) {
        String message = messageSource.getMessage(messageCode, new Object[]{id}, LocaleContextHolder.getLocale());
        return MessageResponseDTO
                .builder()
                .id(id)
                .message(message)
                .build();
    }
}

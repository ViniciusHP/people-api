package one.digitalinnovation.peopleapi.controller;

import lombok.AllArgsConstructor;
import one.digitalinnovation.peopleapi.assembler.PersonDTOModelAssembler;
import one.digitalinnovation.peopleapi.dto.request.PersonDTO;
import one.digitalinnovation.peopleapi.dto.response.MessageResponseDTO;
import one.digitalinnovation.peopleapi.exception.PersonNotFoundException;
import one.digitalinnovation.peopleapi.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/v1/people")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonController {

    private final PersonService personService;
    private final PersonDTOModelAssembler modelAssembler;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MessageResponseDTO> createPerson(@RequestBody @Valid PersonDTO personDTO) {
        MessageResponseDTO messageResponseDTO = personService.createPerson(personDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(messageResponseDTO.getId())
                .toUri();
        return ResponseEntity.created(location).body(messageResponseDTO);
    }

    @GetMapping
    public CollectionModel<EntityModel<PersonDTO>> listAll() {
        List<PersonDTO> allPeople = personService.listAll();
        return modelAssembler.toCollectionModel(allPeople);
    }

    @GetMapping("/{id}")
    public EntityModel<PersonDTO> findById(@PathVariable Long id) throws PersonNotFoundException {
        PersonDTO personDTO = personService.findById(id);
        return modelAssembler.toModel(personDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponseDTO> updateById(@PathVariable Long id, @RequestBody @Valid PersonDTO personDTO) throws PersonNotFoundException {
        MessageResponseDTO messageResponseDTO = personService.updateById(id, personDTO);
        return ResponseEntity.ok(messageResponseDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) throws PersonNotFoundException {
        personService.delete(id);
    }
}

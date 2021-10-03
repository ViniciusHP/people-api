package one.digitalinnovation.peopleapi.assembler;

import lombok.SneakyThrows;
import one.digitalinnovation.peopleapi.controller.PersonController;
import one.digitalinnovation.peopleapi.dto.request.PersonDTO;
import one.digitalinnovation.peopleapi.exception.PersonNotFoundException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PersonDTOModelAssembler implements RepresentationModelAssembler<PersonDTO, EntityModel<PersonDTO>> {

    @SneakyThrows(PersonNotFoundException.class)
    @Override
    public EntityModel<PersonDTO> toModel(PersonDTO personDTO) {
        return EntityModel.of(personDTO,
                linkTo(methodOn(PersonController.class).findById(personDTO.getId())).withSelfRel(),
                linkTo(methodOn(PersonController.class).listAll()).withRel("all"));
    }

    @Override
    public CollectionModel<EntityModel<PersonDTO>> toCollectionModel(Iterable<? extends PersonDTO> entities) {
        List<EntityModel<PersonDTO>> people = StreamSupport
                .stream(entities.spliterator(), false)
                .map(this::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(people,
                linkTo(methodOn(PersonController.class).listAll()).withSelfRel());
    }
}

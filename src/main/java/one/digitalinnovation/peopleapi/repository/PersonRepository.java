package one.digitalinnovation.peopleapi.repository;

import one.digitalinnovation.peopleapi.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}

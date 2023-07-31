package hr.rba.creditcardapplication.repositories;


import hr.rba.creditcardapplication.models.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface PersonRepository extends JpaRepository<Person, Long> {

    Optional<Person> findByOib(final String oib);
    String removePersonByOib(final String oib);
}

package br.com.compass.petapi.repositories;
import br.com.compass.petapi.entities.Pet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.*;

@Repository
public interface PetRepository extends MongoRepository<Pet, String> {
  List<Pet> findByNameIgnoreCaseContaining(String name);

  boolean existsByNameAndBirthDate(String name, LocalDate birthDate);
}

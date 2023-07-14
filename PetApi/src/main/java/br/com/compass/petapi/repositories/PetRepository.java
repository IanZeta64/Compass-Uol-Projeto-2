package br.com.compass.petapi.repositories;
import br.com.compass.petapi.dto.reponses.PetDTOResponse;
import br.com.compass.petapi.entities.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.*;

@Repository
public interface PetRepository extends JpaRepository<Pet, UUID> {
  List<Pet> findByNameIgnoreCaseContaining(String name);

  boolean existsByNameAndBirthDate(String name, LocalDate birthDate);

  Optional<Pet> findByNameAndBirthDate(String name, LocalDate birthDate);

  List<Pet> findAllByIsAdoptedFalse();;
}

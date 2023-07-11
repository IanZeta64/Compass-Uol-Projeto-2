package br.com.compass.AdoptionPetAPI.repositories;
import br.com.compass.AdoptionPetAPI.entities.Pet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface PetRepository extends MongoRepository<Pet, String> {
    List<Pet> findByName(String petName);
}

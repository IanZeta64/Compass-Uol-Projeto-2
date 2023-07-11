package br.com.compass.AdoptionPetAPI.repositories;
import br.com.compass.AdoptionPetAPI.entities.Pet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends MongoRepository<Pet, String> {
}

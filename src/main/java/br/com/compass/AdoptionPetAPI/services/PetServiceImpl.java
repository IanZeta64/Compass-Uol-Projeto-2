package br.com.compass.AdoptionPetAPI.services;
import br.com.compass.AdoptionPetAPI.dto.reponses.PetDTOResponse;
import br.com.compass.AdoptionPetAPI.dto.requests.PetDTORequest;
import br.com.compass.AdoptionPetAPI.entities.Pet;
import br.com.compass.AdoptionPetAPI.exceptions.PetNotFoundException;
import br.com.compass.AdoptionPetAPI.exceptions.*;
import br.com.compass.AdoptionPetAPI.repositories.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {
  private final PetRepository petRepository;

  @Override
  public PetDTOResponse create(PetDTORequest petDTORequest) {
    String petName = petDTORequest.name();
    List<Pet> existingPets = petRepository.findByName(petName);
    if (!existingPets.isEmpty()) {
      throw new DuplicatePetException("A pet with the same name already exists: " + petName);
    }
    Pet pet = new Pet(petDTORequest);
    Pet petReturn = petRepository.save(pet);
    return new PetDTOResponse(petReturn);
  }


  @Override
  public List<PetDTOResponse> findAll() {
    List<Pet> pets = petRepository.findAll();
    return pets.stream().map(PetDTOResponse::new).toList();
  }


  @Override
  public PetDTOResponse getById(String id) {
    Pet petReturn = petRepository.findById(id)
            .orElseThrow(() -> new PetNotFoundException(String.format("Pet not founded by id %s.", id)));
    return new PetDTOResponse(petReturn);
  }


  @Override
  public List<PetDTOResponse> searchByName(String petName) { //4
    var response = petRepository.findByName(petName);
    List<PetDTOResponse> petDTOResponseList = new ArrayList<>();
    response.forEach(pet -> petDTOResponseList.add(new PetDTOResponse(pet)));
    return petDTOResponseList;
  }

  @Override
  public PetDTOResponse update(String id, PetDTORequest petDTORequest) {
    Optional<Pet> petReturn = petRepository.findById(id);
    if (petReturn.isEmpty()) {
      throw new PetNotFoundException(String.format("Pet not founded by id %s. Cannot update pet.", id));
    }

    Pet petUpdate = petReturn.get();
    petUpdate.setName(petDTORequest.name());
    petUpdate.setGender(petDTORequest.gender());
    petUpdate.setSpecie(petDTORequest.specie());
    petUpdate.setBirthDate(petDTORequest.birthDate());
    petUpdate.setModifiedOn(Instant.now());

    return new PetDTOResponse(petRepository.save(petUpdate));
  }


  @Override
  public void delete(String id) {
    if (!petRepository.existsById(id)){
      throw new PetNotFoundException(String.format("Pet not founded by id %s. Cannot delete pet.", id));
    }
    petRepository.deleteById(id);
  }
}

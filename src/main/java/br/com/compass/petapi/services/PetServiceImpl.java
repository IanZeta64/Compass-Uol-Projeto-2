package br.com.compass.petapi.services;
import br.com.compass.petapi.dto.reponses.PetDTOResponse;
import br.com.compass.petapi.dto.requests.PetDTORequest;
import br.com.compass.petapi.entities.Pet;
import br.com.compass.petapi.enums.Gender;
import br.com.compass.petapi.enums.Specie;
import br.com.compass.petapi.exceptions.PetNotFoundException;
import br.com.compass.petapi.exceptions.*;
import br.com.compass.petapi.repositories.PetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PetServiceImpl implements PetService {
  private final PetRepository petRepository;

  @Override
  public PetDTOResponse create(PetDTORequest request) {
    log.info("SERVICE - creating pet");
    if(Boolean.TRUE.equals(petRepository.existsByNameAndBirthDate(request.name(), request.birthDate()))){
    throw new DuplicatedPetException(String.format("A pet with name %s and birth date %s already exists.",
      request.name(), request.birthDate()));
    }
    log.info("REPOSITORY - creating pet");
    Pet petSaved = petRepository.save(new Pet(request));
    return new PetDTOResponse(petSaved);
  }

  @Override
  public List<PetDTOResponse> findAll() {
    log.info("SERVICE - finding all pets");
    return petRepository.findAll().stream().map(PetDTOResponse::new).toList();
  }
  @Override
  public PetDTOResponse getById(String id) {
    log.info("SERVICE - getting pet by ID");
    Pet petReturn = petRepository.findById(UUID.fromString(id))
            .orElseThrow(() -> new PetNotFoundException(String.format("Pet not founded by id %s.", id)));
    log.info("SERVICE - returning pet got by id to CONTROLLER");
    return new PetDTOResponse(petReturn);
  }

  @Override
  public List<PetDTOResponse> searchByName(String name) {
    log.info("SERVICE - searching pet by name");
    return petRepository.findByNameIgnoreCaseContaining(name).stream().map(PetDTOResponse::new).toList();
  }

  @Override
  public PetDTOResponse update(String id, PetDTORequest request) {
    log.info("SERVICE - updating pet");
    Pet petUpdate = petRepository.findById(UUID.fromString(id)).map(pet -> new Pet(
      pet.getId(), request.name(), Gender.valueOf(request.gender()), Specie.valueOf(request.specie()), pet.getIsAdopted(),
        request.birthDate(), pet.getRegisterOn(), Instant.now())
      ).orElseThrow(() -> new PetNotFoundException(String.format("Pet not founded by id %s. Cannot update pet.", id)));
    log.info("SERVICE - returning updated pet to CONTROLLER");
    return new PetDTOResponse(petRepository.save(petUpdate));
  }

  @Override
  public void delete(String id) {
    log.info("SERVICE - deleting pet");
    Pet pet = petRepository.findById(UUID.fromString(id)).orElseThrow(() -> new PetNotFoundException(String.format("Pet not founded by id %s. Cannot delete pet.", id)));
    petRepository.delete(pet);
  }

  @Override
  public PetDTOResponse patchStatus(String id) {
    log.info("SERVICE - updating isAdopted status");
    Pet petUpdate = petRepository.findById(UUID.fromString(id)).map(pet -> {
        pet.setAdopted();
        return pet;
      }).orElseThrow(() -> new PetNotFoundException(String.format("Pet not found by id %s. Cannot update pet adoption.", id)));
    log.info("SERVICE - returning updated pet to CONTROLLER");
    return new PetDTOResponse(petRepository.save(petUpdate));

  }
}

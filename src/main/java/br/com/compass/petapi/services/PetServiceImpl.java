package br.com.compass.petapi.services;
import br.com.compass.petapi.dto.reponses.PetDTOResponse;
import br.com.compass.petapi.dto.requests.PetDTORequest;
import br.com.compass.petapi.entities.Pet;
import br.com.compass.petapi.exceptions.PetNotFoundException;
import br.com.compass.petapi.exceptions.*;
import br.com.compass.petapi.repositories.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {
  private final PetRepository petRepository;

  @Override
  public PetDTOResponse create(PetDTORequest request) {
    if(Boolean.TRUE.equals(petRepository.existsByNameAndBirthDate(request.name(), request.birthDate()))){
      throw new DuplicatedPetException(String.format("A pet with name %s and birth date %s already exists.",
        request.name(), request.birthDate()));
    }
    Pet petSaved = petRepository.save(new Pet(request));
    return new PetDTOResponse(petSaved);
  }

  @Override
  public List<PetDTOResponse> findAll() {
    return petRepository.findAll().stream().map(PetDTOResponse::new).toList();
  }

  @Override
  public PetDTOResponse getById(String id) {
    Pet petReturn = petRepository.findById(id)
            .orElseThrow(() -> new PetNotFoundException(String.format("Pet not founded by id %s.", id)));
    return new PetDTOResponse(petReturn);
  }

  @Override
  public List<PetDTOResponse> searchByName(String name) {
    return petRepository.findByNameIgnoreCaseContaining(name).stream().map(PetDTOResponse::new).toList();
  }

  @Override
  public PetDTOResponse update(String id, PetDTORequest request) {
    Pet petUpdate = petRepository.findById(id).map(pet -> new Pet(
      pet.getId(), request.name(), request.gender(), request.specie(), pet.getIsAdopted(),
        request.birthDate(), pet.getRegisterOn(), Instant.now())
      ).orElseThrow(() -> new PetNotFoundException(String.format("Pet not founded by id %s. Cannot update pet.", id)));
    return new PetDTOResponse(petRepository.save(petUpdate));
  }

  @Override
  public void delete(String id) {
    petRepository.findById(id).ifPresentOrElse(pet -> petRepository.deleteById(id),
      () -> {
        throw new PetNotFoundException(String.format("Pet not founded by id %s. Cannot delete pet.", id));
      });
  }

  @Override
  public PetDTOResponse patchStatus(String id) {
    Pet petUpdate = petRepository.findById(id).map(pet -> {
        pet.setAdopted();
        return pet;
      }).orElseThrow(() -> new PetNotFoundException(String.format("Pet not found by id %s. Cannot update pet adoption.", id)));
    return new PetDTOResponse(petRepository.save(petUpdate));

  }
}

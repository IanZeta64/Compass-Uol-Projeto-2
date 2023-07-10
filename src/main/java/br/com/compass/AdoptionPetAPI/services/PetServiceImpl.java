package br.com.compass.AdoptionPetAPI.services;
import br.com.compass.AdoptionPetAPI.dto.reponses.PetDTOResponse;
import br.com.compass.AdoptionPetAPI.dto.requests.PetDTORequest;
import br.com.compass.AdoptionPetAPI.entities.Pet;
import br.com.compass.AdoptionPetAPI.enums.Gender;
import br.com.compass.AdoptionPetAPI.enums.Specie;
import br.com.compass.AdoptionPetAPI.repositories.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {
  private final PetRepository petRepository;

  @Override
  public PetDTOResponse create(PetDTORequest petDTORequest) { //3
    return null;
  }

  @Override
  public List<PetDTOResponse> findAll() {
    return null;
  }

  @Override
  public PetDTOResponse getById(String id) {
    Pet petReturn = petRepository.findById(id).orElseThrow(() -> new RuntimeException(""));
    return new PetDTOResponse(petReturn.getId(), petReturn.getName(), petReturn.getGender(),
      petReturn.getSpecie(), petReturn.getIsAdopted(), petReturn.getBirthDate());
  }

  @Override
  public PetDTOResponse searchByName(PetDTORequest petDTORequest) { //4
    return null;
  }

  @Override
  public PetDTOResponse update(String id, PetDTORequest petDTORequest) { //5
    return null;
  }

  @Override
  public void delete(String id) {
  }
}

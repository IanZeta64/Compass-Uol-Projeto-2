package br.com.compass.AdoptionPetAPI.services;
import br.com.compass.AdoptionPetAPI.dto.reponses.PetDTOResponse;
import br.com.compass.AdoptionPetAPI.dto.requests.PetDTORequest;
import br.com.compass.AdoptionPetAPI.entities.Pet;
import br.com.compass.AdoptionPetAPI.enums.Gender;
import br.com.compass.AdoptionPetAPI.enums.Specie;
import br.com.compass.AdoptionPetAPI.exceptions.ListIsEmpty;
import br.com.compass.AdoptionPetAPI.exceptions.PetIdNotFoundException;
import br.com.compass.AdoptionPetAPI.repositories.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {
  private final PetRepository petRepository;

  @Override
  public PetDTOResponse create(PetDTORequest petDTORequest) { //3
    Pet pet = new Pet(petDTORequest);
    Pet petReturn = petRepository.save(pet);
    return new PetDTOResponse(petReturn.getId(), petReturn.getName(), petReturn.getGender(), petReturn.getSpecie(), petReturn.getIsAdopted(), petReturn.getBirthDate());
  }

  @Override
  public List<PetDTOResponse> findAll() {
    List<Pet> pets = petRepository.findAll();
    return pets.stream()
            .map(pet -> new PetDTOResponse(
                    pet.getId(),
                    pet.getName(),
                    pet.getGender(),
                    pet.getSpecie(),
                    pet.getIsAdopted(),
                    pet.getBirthDate()
            ))
            .toList();
  }


  @Override
  public PetDTOResponse getById(String id) {
    Pet petReturn = petRepository.findById(id).orElseThrow(() -> new RuntimeException(""));
    return new PetDTOResponse(petReturn.getId(), petReturn.getName(), petReturn.getGender(),
      petReturn.getSpecie(), petReturn.getIsAdopted(), petReturn.getBirthDate());
  }

  @Override
  public List<PetDTOResponse> searchByName(String petName) { //4
    var response = petRepository.findByName(petName);
    List<PetDTOResponse> petDTOResponseList = new ArrayList<>();
    if(response.isEmpty()){
      throw new ListIsEmpty();
    }
    response.forEach(pet -> petDTOResponseList.add(new PetDTOResponse(pet.getId(),
            pet.getName(), pet.getGender(), pet.getSpecie(), pet.getIsAdopted(),pet.getBirthDate())));
    return petDTOResponseList;
  }

  @Override
  public PetDTOResponse update(String id, PetDTORequest petDTORequest) { //5

    Optional<Pet> petReturn = petRepository.findById(id);
    if(petReturn.isEmpty()){
      throw new PetIdNotFoundException();
    }
    Pet petUpdate = petReturn.get();
    petUpdate.setName(petDTORequest.name());
    petUpdate.setGender(petDTORequest.gender());
    petUpdate.setSpecie(petDTORequest.specie());
    petUpdate.setBirthDate(petDTORequest.birthDate());
    petUpdate.setModifiedOn(Instant.now());
    petRepository.save(petUpdate);
    return new PetDTOResponse(petUpdate.getId(), petUpdate.getName(),
            petUpdate.getGender(), petUpdate.getSpecie(), petUpdate.getIsAdopted(), petUpdate.getBirthDate());
  }

  @Override
  public void delete(String id) {
    if (!petRepository.existsById(id)){
      throw new RuntimeException("ERRO");
    }
    petRepository.deleteById(id);
  }
}

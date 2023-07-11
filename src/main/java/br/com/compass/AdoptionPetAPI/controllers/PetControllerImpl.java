package br.com.compass.AdoptionPetAPI.controllers;
import br.com.compass.AdoptionPetAPI.dto.reponses.PetDTOResponse;
import br.com.compass.AdoptionPetAPI.dto.requests.PetDTORequest;
import br.com.compass.AdoptionPetAPI.services.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PetControllerImpl implements PetController{

  private final PetService petService;


  @Override
  public PetDTOResponse create(PetDTORequest petDTORequest) {
    return petService.create(petDTORequest);
  }

  @Override
  public List<PetDTOResponse> findAll() {
    return null;
  }

  @Override
  public PetDTOResponse getById(String id) {
    return petService.getById(id);
  }

  @Override
  public List<PetDTOResponse> searchByName(String petName) {
    return petService.searchByName(petName);
  }

  @Override
  public PetDTOResponse update(String id, PetDTORequest petDTORequest) {
    return petService.update(id, petDTORequest);
  }

  @Override
  public void delete(String id) {
    petService.delete(id);
  }
}

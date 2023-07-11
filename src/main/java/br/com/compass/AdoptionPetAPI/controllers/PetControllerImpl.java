package br.com.compass.AdoptionPetAPI.controllers;
import br.com.compass.AdoptionPetAPI.dto.reponses.PetDTOResponse;
import br.com.compass.AdoptionPetAPI.dto.requests.PetDTORequest;
import br.com.compass.AdoptionPetAPI.services.PetService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PetControllerImpl implements PetController{

  private final PetService petService;



  @Override
  public ResponseEntity<PetDTOResponse> create(PetDTORequest petDTORequest, UriComponentsBuilder builder) {
    var response = petService.create(petDTORequest);
    var uri = builder.path("/api/v1/pet/{id}").buildAndExpand(response.id()).toUri();
    return ResponseEntity.created(uri).body(response);
  }

  @Override
  public ResponseEntity<List<PetDTOResponse>> findAll() {
    var response = petService.findAll();
    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<PetDTOResponse> getById(String id) {
    var response = petService.getById(id);
    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<List<PetDTOResponse>> searchByName(String petName) {
    var response = petService.searchByName(petName);;
    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<PetDTOResponse> update(String id, PetDTORequest petDTORequest, UriComponentsBuilder builder) {
    var response = petService.update(id, petDTORequest);
    var uri = builder.path("/api/v1/pet/{id}").buildAndExpand(response.id()).toUri();
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @Override
  public ResponseEntity<Void> delete(String id) {
    petService.delete(id);
    return ResponseEntity.noContent().build();
  }
}

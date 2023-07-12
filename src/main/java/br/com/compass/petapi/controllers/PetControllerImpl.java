package br.com.compass.petapi.controllers;
import br.com.compass.petapi.dto.reponses.PetDTOResponse;
import br.com.compass.petapi.dto.requests.PetDTORequest;
import br.com.compass.petapi.services.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PetControllerImpl implements PetController{
  private final PetService petService;
  private static final String PATH_ID = "/api/v1/pet/{id}";

  @Override
  public ResponseEntity<PetDTOResponse> create(PetDTORequest request, UriComponentsBuilder builder) {
    var response = petService.create(request);
    var uri = builder.path(PATH_ID).buildAndExpand(response.id()).toUri();
    return ResponseEntity.created(uri).body(response);
  }

  @Override
  public ResponseEntity<List<PetDTOResponse>> findAll() {
    return ResponseEntity.ok(petService.findAll());
  }

  @Override
  public ResponseEntity<PetDTOResponse> getById(String id) {
    return ResponseEntity.ok(petService.getById(id));
  }

  @Override
  public ResponseEntity<List<PetDTOResponse>> searchByName(String name) {
    return ResponseEntity.ok(petService.searchByName(name));
  }

  @Override
  public ResponseEntity<PetDTOResponse> update(String id, PetDTORequest request, UriComponentsBuilder builder) {
    var response = petService.update(id, request);
    var uri = builder.path(PATH_ID).buildAndExpand(response.id()).toUri();
    return ResponseEntity.ok().location(uri).body(response);
  }

  @Override
  public ResponseEntity<Void> delete(String id) {
    petService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<PetDTOResponse> patchStatus(String id, UriComponentsBuilder builder) {
    var response = petService.patchStatus(id);
    var uri = builder.path(PATH_ID).buildAndExpand(response.id()).toUri();
    return ResponseEntity.ok().location(uri).body(response);
  }
}

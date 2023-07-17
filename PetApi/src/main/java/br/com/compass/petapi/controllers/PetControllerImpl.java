package br.com.compass.petapi.controllers;
import br.com.compass.petapi.dto.reponses.PetDTOResponse;
import br.com.compass.petapi.dto.requests.PetDTORequest;
import br.com.compass.petapi.services.PetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PetControllerImpl implements PetController{
  private final PetService petService;
  private static final String PATH_ID = "/api/v1/pet/{id}";

  @Override
  public ResponseEntity<PetDTOResponse> create(PetDTORequest request, UriComponentsBuilder builder) {
    log.info("CONTROLLER - creating pet");
    var response = petService.create(request);
    var uri = builder.path(PATH_ID).buildAndExpand(response.id()).toUri();
    log.info("CONTROLLER - returning created pet");
    return ResponseEntity.created(uri).body(response);
  }

  @Override
  public ResponseEntity<List<PetDTOResponse>> findAll() {
    log.info("CONTROLLER - finding all pets");
    return ResponseEntity.ok(petService.findAll());
  }

  @Override
  public ResponseEntity<List<PetDTOResponse>> findAllNotAdopted() {
    log.info("CONTROLLER - finding all not adopoted pets");
    return ResponseEntity.ok(petService.findAllNotAdopted());
  }

  @Override
  public ResponseEntity<PetDTOResponse> getById(String id) {
    log.info("CONTROLLER - getting pet by ID");
    return ResponseEntity.ok(petService.getById(id));
  }

  @Override
  public ResponseEntity<List<PetDTOResponse>> searchByName(String name) {
    log.info("CONTROLLER - searching pet by name");
    return ResponseEntity.ok(petService.searchByName(name));
  }

  @Override
  public ResponseEntity<PetDTOResponse> update(String id, PetDTORequest request, UriComponentsBuilder builder) {
    log.info("CONTROLLER - updating pet");
    var response = petService.update(id, request);
    var uri = builder.path(PATH_ID).buildAndExpand(response.id()).toUri();
    log.info("CONTROLLER - returning updated pet");
    return ResponseEntity.ok().location(uri).body(response);
  }

  @Override
  public ResponseEntity<Void> delete(String id) {
    log.info("CONTROLLER - deleting pet");
    petService.delete(id);
    log.info("CONTROLLER - returning HTTP status");
    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<PetDTOResponse> patchStatus(String id) {
    log.info("CONTROLLER - updating isAdopted status");
    var response = petService.patchStatus(id);
    var uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
      .pathSegment(String.valueOf(response.id()))
      .build()
      .toUri();
    log.info("CONTROLLER - returning updated pet");
    return ResponseEntity.ok().location(uri).body(response);
  }
}

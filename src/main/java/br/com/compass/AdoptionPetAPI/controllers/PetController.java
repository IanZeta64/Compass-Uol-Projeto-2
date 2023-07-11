package br.com.compass.AdoptionPetAPI.controllers;
import br.com.compass.AdoptionPetAPI.dto.reponses.PetDTOResponse;
import br.com.compass.AdoptionPetAPI.dto.requests.PetDTORequest;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RequestMapping("/api/v1/pet")
public interface PetController {

  @PostMapping
  public ResponseEntity<PetDTOResponse> create(@RequestBody PetDTORequest petDTORequest, UriComponentsBuilder builder);
  @GetMapping
  public ResponseEntity<List<PetDTOResponse>> findAll();
  @GetMapping("/{id}")
  public ResponseEntity<PetDTOResponse> getById(@PathVariable String id);

  @GetMapping(params = "petName")
  public ResponseEntity<List<PetDTOResponse>> searchByName(@RequestParam String petName);

  @PutMapping("/{id}")
  public ResponseEntity<PetDTOResponse> update(@PathVariable String id, @RequestBody PetDTORequest petDTORequest, UriComponentsBuilder builder);

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable String id);

}

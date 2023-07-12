package br.com.compass.petapi.controllers;
import br.com.compass.petapi.dto.reponses.PetDTOResponse;
import br.com.compass.petapi.dto.requests.PetDTORequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RequestMapping("${request.mapping}")
public interface PetController {

  @PostMapping
  public ResponseEntity<PetDTOResponse> create(@RequestBody PetDTORequest request, UriComponentsBuilder builder);
  @GetMapping
  public ResponseEntity<List<PetDTOResponse>> findAll();
  @GetMapping("/{id}")
  public ResponseEntity<PetDTOResponse> getById(@PathVariable String id);

  @GetMapping(params = "petName")
  public ResponseEntity<List<PetDTOResponse>> searchByName(@RequestParam String name);

  @PutMapping("/{id}")
  public ResponseEntity<PetDTOResponse> update(@PathVariable String id, @RequestBody PetDTORequest request, UriComponentsBuilder builder);

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable String id);

  @PatchMapping("/{id}")
  public ResponseEntity<PetDTOResponse> patchStatus(@PathVariable String id, UriComponentsBuilder builder);

}

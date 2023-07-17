package br.com.compass.petapi.controllers;
import br.com.compass.petapi.dto.reponses.PetDTOResponse;
import br.com.compass.petapi.dto.requests.PetDTORequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RequestMapping("/api/v1/pet")
public interface PetController {

  @PostMapping
  ResponseEntity<PetDTOResponse> create(@Validated @RequestBody PetDTORequest request, UriComponentsBuilder builder);
  @GetMapping
  ResponseEntity<List<PetDTOResponse>> findAll();

  @GetMapping("/notAdopted")
  ResponseEntity<List<PetDTOResponse>> findAllNotAdopted();
  @GetMapping("/{id}")
  ResponseEntity<PetDTOResponse> getById(@PathVariable String id);

  @GetMapping(value = "/search", params = "name")
  ResponseEntity<List<PetDTOResponse>> searchByName(@RequestParam String name);

  @PutMapping("/{id}")
  ResponseEntity<PetDTOResponse> update(@PathVariable String id, @Validated @RequestBody PetDTORequest request, UriComponentsBuilder builder);

  @DeleteMapping("/{id}")
  ResponseEntity<Void> delete(@PathVariable String id);

  @PatchMapping("/alterAdoptedStatus/{id}")
  ResponseEntity<PetDTOResponse> patchStatus(@PathVariable String id);

}

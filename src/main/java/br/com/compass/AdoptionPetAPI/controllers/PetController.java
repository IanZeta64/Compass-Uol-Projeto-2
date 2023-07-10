package br.com.compass.AdoptionPetAPI.controllers;
import br.com.compass.AdoptionPetAPI.dto.reponses.PetDTOResponse;
import br.com.compass.AdoptionPetAPI.dto.requests.PetDTORequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/pet")
public interface PetController {

  @PostMapping
  public PetDTOResponse create(@RequestBody PetDTORequest petDTORequest);
  @GetMapping
  public List<PetDTOResponse> findAll();
  @GetMapping("/{id}")
  public PetDTOResponse getById(@PathVariable String id);

  @GetMapping(params = "petName")
  public List<PetDTOResponse> searchByName(@RequestParam String petName);

  @PutMapping("/{id}")
  public PetDTOResponse update(@PathVariable String id, @RequestBody PetDTORequest petDTORequest);

  @DeleteMapping("/{id}")
  public void delete(@PathVariable String id);

}

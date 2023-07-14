package br.com.compass.adoptionapi.clients.repositories;

import br.com.compass.adoptionapi.clients.dto.PetDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "PetRepositoryOpenFeing", url = "/api/v1/pet")
public interface PetRepositoryOpenFeing {
//
//  @GetMapping("/notAdopted")
//  List<PetDTO> findAllPetNotAdopted();

  @GetMapping("/{id}")
  PetDTO getPetById(@PathVariable String id);

  @PatchMapping("/{id}")
  PetDTO patchStatusPet(@PathVariable String id);
}

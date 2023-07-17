package br.com.compass.adoptionapi.clients.repositories;

import br.com.compass.adoptionapi.clients.dto.PetDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "PetRepositoryFeignClient", url = "http://localhost:8081/api/v1/pet")
public interface PetRepositoryFeignClient {

  @GetMapping("/{id}")
  PetDTO getPetById(@PathVariable String id);

  @PatchMapping("/alterAdoptedStatus/{id}")
  void patchStatusPet(@PathVariable String id);
}

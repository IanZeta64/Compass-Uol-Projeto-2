package br.com.compass.AdoptionPetAPI.dto.reponses;
import br.com.compass.AdoptionPetAPI.entities.Pet;
import br.com.compass.AdoptionPetAPI.enums.Gender;
import br.com.compass.AdoptionPetAPI.enums.Specie;

import java.time.LocalDate;

public record PetDTOResponse(String id, String name, Gender gender, Specie specie, Boolean isAdopted, LocalDate birthDate) {
  public PetDTOResponse(Pet pet) {
    this(pet.getId(), pet.getName(), pet.getGender(), pet.getSpecie(), pet.getIsAdopted(), pet.getBirthDate());
  }
}

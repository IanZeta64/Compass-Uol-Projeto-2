package br.com.compass.petapi.dto.reponses;
import br.com.compass.petapi.entities.Pet;
import br.com.compass.petapi.enums.Gender;
import br.com.compass.petapi.enums.Specie;

import java.time.LocalDate;

public record PetDTOResponse(String id, String name, Gender gender, Specie specie, Boolean isAdopted, LocalDate birthDate) {
  public PetDTOResponse(Pet pet) {
    this(pet.getId(), pet.getName(), pet.getGender(), pet.getSpecie(), pet.getIsAdopted(), pet.getBirthDate());
  }
}

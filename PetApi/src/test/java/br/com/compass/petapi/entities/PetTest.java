package br.com.compass.petapi.entities;
import br.com.compass.petapi.dto.requests.PetDTORequest;
import br.com.compass.petapi.enums.Gender;
import br.com.compass.petapi.enums.Specie;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class PetTest {

  @ParameterizedTest
  @MethodSource("br.com.compass.petapi.dummy.DummyPet#generatePets")
  @DisplayName("PET @ParamTest - test setAdopted method")
  void mustTestChangesOnIsAdoptedAndModifiedOnParameterized(Pet pet) {
    boolean isAdopted = pet.getIsAdopted();
    Instant modifiedOn = pet.getModifiedOn();
    pet.setAdopted();
    assertNotEquals(isAdopted, pet.getIsAdopted());
    assertNotEquals(modifiedOn, pet.getModifiedOn());
  }

  @ParameterizedTest
  @MethodSource("br.com.compass.petapi.dummy.DummyPet#generateDTORequests")
  @DisplayName("PET ParamTest - test custom constructor")
  void mustTestCustomConstructor(PetDTORequest request){
    Pet pet = new Pet(request);
    assertEquals(request.name(), pet.getName());
    assertEquals(Gender.valueOf(request.gender()), pet.getGender());
    assertEquals(Specie.valueOf(request.specie()), pet.getSpecie());
    assertEquals(request.birthDate(), pet.getBirthDate());

    assertNull(pet.getId());
    assertFalse(pet.getIsAdopted());
    assertNotNull(pet.getRegisterOn());
    assertNull(pet.getModifiedOn());

  }
}

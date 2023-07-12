package br.com.compass.petapi.entities;

import br.com.compass.petapi.dto.requests.PetDTORequest;
import br.com.compass.petapi.enums.Gender;
import br.com.compass.petapi.enums.Specie;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class PetTest {

  private Pet pet;
  private String id;

  @BeforeEach
  void setUp() {
    this.id = "83538b0e-0f17-4dd2-a784-5da32deb1ed4";
  this.pet = new Pet(UUID.fromString(id), "Toto", Gender.MALE, Specie.DOG, true, LocalDate.of(2020, 10, 30), Instant.now(), Instant.now());
  }

  @AfterEach
  void tearDown() {

  }

  @Test
  @DisplayName("PET @Test - test setAdopted method")
  @SneakyThrows
  void mustTestChangesOnIsAdoptedAndModifiedOn() {
    boolean isAdopted = this.pet.getIsAdopted();
    Instant modifiedOn = this.pet.getModifiedOn();
    Thread.sleep(500);
    this.pet.setAdopted();
    assertNotEquals(isAdopted, this.pet.getIsAdopted());
    assertNotEquals(modifiedOn, this.pet.getModifiedOn());
  }

  @ParameterizedTest
  @MethodSource("generatePets")
  @DisplayName("PET @ParamTest - test setAdopted method")
  @SneakyThrows
  void mustTestChangesOnIsAdoptedAndModifiedOnParameterized(Pet pet) {
    boolean isAdopted = pet.getIsAdopted();
    Instant modifiedOn = pet.getModifiedOn();
    Thread.sleep(500);
    pet.setAdopted();
    assertNotEquals(isAdopted, pet.getIsAdopted());
    assertNotEquals(modifiedOn, pet.getModifiedOn());
  }

  @ParameterizedTest
  @MethodSource("generateDTORequests")
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

  private static Stream<Arguments> generatePets(){
    return Stream.of(
      Arguments.of(new Pet(UUID.randomUUID(), "Toto", Gender.MALE, Specie.DOG, false, LocalDate.of(2020, 10, 30), Instant.now(), null)
      ),
      Arguments.of(
        new Pet(UUID.randomUUID(), "Belinha", Gender.FEMALE, Specie.DOG, true, LocalDate.of(2022, 10, 30), Instant.now(), Instant.now())
      ),
      Arguments.of(
        new Pet(UUID.randomUUID(), "Gatinho", Gender.MALE, Specie.CAT, false, LocalDate.of(2020, 10, 30), Instant.now(), Instant.now())
      )
    );
  }

  private static Stream<Arguments> generateDTORequests(){
    return Stream.of(
      Arguments.of(
        new PetDTORequest("Toto", "MALE", "DOG", LocalDate.of(2022, 10, 30))
      ),
      Arguments.of(
        new PetDTORequest("Belinha", "FEMALE", "DOG", LocalDate.of(2022, 10, 30))
      ),
      Arguments.of(
        new PetDTORequest("Gatinho", "MALE", "CAT", LocalDate.of(2022, 10, 30))
      )
    );
  }

}

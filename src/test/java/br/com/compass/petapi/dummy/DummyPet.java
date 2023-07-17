package br.com.compass.petapi.dummy;

import br.com.compass.petapi.dto.reponses.PetDTOResponse;
import br.com.compass.petapi.dto.requests.PetDTORequest;
import br.com.compass.petapi.entities.Pet;
import br.com.compass.petapi.enums.Gender;
import br.com.compass.petapi.enums.Specie;
import org.junit.jupiter.params.provider.Arguments;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.stream.Stream;

public class DummyPet {
  private static final PetDTORequest request1 =  new PetDTORequest("Toto", "MALE", "DOG", LocalDate.of(2022, 10, 30));
  private static final PetDTORequest request2 = new PetDTORequest("Belinha", "FEMALE", "DOG", LocalDate.of(2022, 10, 30));
  private static final PetDTORequest request3 = new PetDTORequest("Gatinho", "MALE", "CAT", LocalDate.of(2022, 10, 30));

  private static final PetDTORequest badRequest1 = new PetDTORequest("ABCSADGEAD12435&*as9f9#$refmdklASJDSDF", "MALE", "DOG", LocalDate.of(2020, 10, 30));
  private static final PetDTORequest getBadRequest2 = new PetDTORequest("Belinha", "female", "sheep", LocalDate.of(2022, 10, 30));
  private static final PetDTORequest getBadRequest3 = new PetDTORequest("Gatinho", "MALE", "CAT", LocalDate.of(2032, 10, 30));

  public static Stream<Arguments> generateDTORequests(){
    return Stream.of(
      Arguments.of(request1),  Arguments.of(request2),  Arguments.of(request3)
    );
  }

  public static Stream<Arguments> generateBadDTORequests(){
    return Stream.of(
      Arguments.of(badRequest1),  Arguments.of(badRequest1),  Arguments.of(badRequest1)
    );
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
  public static Stream<Arguments> generateId() {
    return Stream.of(
      Arguments.of(UUID.randomUUID().toString()),
      Arguments.of(UUID.randomUUID().toString()),
      Arguments.of(UUID.randomUUID().toString())
    );
  }

  public static PetDTOResponse returnResponseFromRequest(PetDTORequest petDTORequest){
    return new PetDTOResponse(UUID.randomUUID(), petDTORequest.name(), Gender.valueOf(petDTORequest.gender()),
      Specie.valueOf(petDTORequest.specie()), false, petDTORequest.birthDate());
  }

  public static PetDTOResponse returnADoptedPetResponseFromRequest(PetDTORequest petDTORequest){
    return new PetDTOResponse(UUID.randomUUID(), petDTORequest.name(), Gender.valueOf(petDTORequest.gender()),
      Specie.valueOf(petDTORequest.specie()), true, petDTORequest.birthDate());
  }
}

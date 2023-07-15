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
  private static final PetDTORequest REQUEST1 =
    new PetDTORequest("Toto", "MALE", "DOG", LocalDate.of(2022, 10, 30));
  private static final PetDTORequest REQUEST2 =
    new PetDTORequest("Belinha", "FEMALE", "DOG", LocalDate.of(2022, 10, 30));
  private static final PetDTORequest REQUEST3 =
    new PetDTORequest("Gatinho", "MALE", "CAT", LocalDate.of(2022, 10, 30));

  private static final PetDTORequest BADREQUEST1 =
    new PetDTORequest("ABCSADGEAD12435&*as9f9#$refmdklASJDSDF", "MALE", "DOG", LocalDate.of(2020, 10, 30));
  private static final PetDTORequest BADREQUEST2 =
    new PetDTORequest("Belinha", "female", "sheep", LocalDate.of(2022, 10, 30));
  private static final PetDTORequest BADREQUEST3 =
    new PetDTORequest("Gatinho", "MALE", "CAT", LocalDate.of(2032, 10, 30));

  public static Stream<Arguments> generateDTORequests(){
    return Stream.of(
      Arguments.of(REQUEST1),  Arguments.of(REQUEST2),  Arguments.of(REQUEST3)
    );
  }

  public static Stream<Arguments> generateBadDTORequests(){
    return Stream.of(
      Arguments.of(BADREQUEST1),  Arguments.of(BADREQUEST2),  Arguments.of(BADREQUEST3)
    );
  }
  private static Stream<Arguments> generatePets(){
    return Stream.of(
      Arguments.of(new Pet(REQUEST1)
      ),
      Arguments.of(
        new Pet(REQUEST2)
      ),
      Arguments.of(
        new Pet(REQUEST3)
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

  public static PetDTOResponse returnResponseFromRequest(PetDTORequest request){
    return new PetDTOResponse(UUID.randomUUID(), request.name(), Gender.valueOf(request.gender()),
      Specie.valueOf(request.specie()), false, request.birthDate());
  }

  public static PetDTOResponse returnADoptedPetResponseFromRequest(PetDTORequest request){
    return new PetDTOResponse(UUID.randomUUID(), request.name(), Gender.valueOf(request.gender()),
      Specie.valueOf(request.specie()), true, request.birthDate());
  }
}

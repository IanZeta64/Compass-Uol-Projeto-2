package br.com.compass.adoptionapi.dummy;
import br.com.compass.adoptionapi.clients.dto.PetDTO;
import br.com.compass.adoptionapi.dto.requests.AdoptionDocDTORequest;
import br.com.compass.adoptionapi.dto.responses.AdoptionDocDTOResponse;
import br.com.compass.adoptionapi.entities.AdoptionDoc;
import org.junit.jupiter.params.provider.Arguments;

import java.time.Instant;
import java.util.UUID;
import java.util.stream.Stream;

public class DummyAdoptionDoc {
    private static final AdoptionDocDTORequest REQUEST1 =
      new AdoptionDocDTORequest("543fb418-25d4-477c-b66c-b3288e167bc7", "Cleber");
  private static final AdoptionDocDTORequest REQUEST2 =
    new AdoptionDocDTORequest("943983c3-f6f2-42a8-b90a-338b5eb76a68", "Joao");
  private static final AdoptionDocDTORequest REQUEST3 =
    new AdoptionDocDTORequest("d6bbb256-a5d5-47e9-8b54-43a4de60cec9", "Maria");

    private static final AdoptionDocDTORequest BADREQUEST1 =
      new AdoptionDocDTORequest("1", "Cleber");
    private static final AdoptionDocDTORequest BADREQUEST2 =
      new AdoptionDocDTORequest("1", null);
    private static final AdoptionDocDTORequest BADREQUEST3 =
      new AdoptionDocDTORequest(null, null);

    public static Stream<Arguments> generateDTORequests(){
      return Stream.of(
        Arguments.of(REQUEST1),  Arguments.of(REQUEST2),  Arguments.of(REQUEST3)
      );
    }

  public static AdoptionDoc returnEntitiesFromRequest(AdoptionDocDTORequest request){
    return new AdoptionDoc(UUID.randomUUID(), UUID.fromString(request.petId()),
      request.tutorName(), Instant.now(), null);
  }



    public static Stream<Arguments> generateBadDTORequests(){
      return Stream.of(
        Arguments.of(BADREQUEST1),  Arguments.of(BADREQUEST2),  Arguments.of(BADREQUEST3)
      );
    }
    private static Stream<Arguments> generateEntities(){
      return Stream.of(
        Arguments.of(new AdoptionDoc(REQUEST1)),
        Arguments.of(new AdoptionDoc(REQUEST2)),
        Arguments.of(new AdoptionDoc(REQUEST3))
      );
    }
    public static Stream<Arguments> generateId() {
      return Stream.of(
        Arguments.of(UUID.randomUUID().toString()),
        Arguments.of(UUID.randomUUID().toString()),
        Arguments.of(UUID.randomUUID().toString())
      );
    }

    public static AdoptionDocDTOResponse returnResponseFromRequest(AdoptionDocDTORequest request){
      return new AdoptionDocDTOResponse(UUID.randomUUID(), UUID.fromString(request.petId()), request.tutorName());
    }

    public static PetDTO returnPetDTO(String id){
      return new PetDTO(UUID.fromString(id), "Toto", false);
    }

}

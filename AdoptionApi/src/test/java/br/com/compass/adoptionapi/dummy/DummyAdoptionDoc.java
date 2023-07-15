package br.com.compass.adoptionapi.dummy;
import br.com.compass.adoptionapi.dto.requests.AdoptionDocDTORequest;
import br.com.compass.adoptionapi.dto.responses.AdoptionDocDTOResponse;
import br.com.compass.adoptionapi.entities.AdoptionDoc;
import org.junit.jupiter.params.provider.Arguments;
import java.util.UUID;
import java.util.stream.Stream;

public class DummyAdoptionDoc {
    private static final AdoptionDocDTORequest REQUEST1 =
      new AdoptionDocDTORequest("c7bafa2b-d204-45e9-bb01-0de8b1da45bc", "Cleber");
  private static final AdoptionDocDTORequest REQUEST2 =
    new AdoptionDocDTORequest("23db56d1-ca3a-4663-b4c4-9b80cc1fa6ae", "Joao");
  private static final AdoptionDocDTORequest REQUEST3 =
    new AdoptionDocDTORequest("e4173701-d2e9-4d48-82f2-81095cd5061e", "Maria");

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

    public static Stream<Arguments> generateBadDTORequests(){
      return Stream.of(
        Arguments.of(BADREQUEST1),  Arguments.of(BADREQUEST2),  Arguments.of(BADREQUEST3)
      );
    }
    private static Stream<Arguments> generateEtities(){
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

}

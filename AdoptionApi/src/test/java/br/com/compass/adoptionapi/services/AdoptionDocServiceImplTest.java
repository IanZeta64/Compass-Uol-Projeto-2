package br.com.compass.adoptionapi.services;
import br.com.compass.adoptionapi.clients.dto.PetDTO;
import br.com.compass.adoptionapi.clients.repositories.PetRepositoryFeignClient;
import br.com.compass.adoptionapi.dto.requests.AdoptionDocDTORequest;
import br.com.compass.adoptionapi.dto.responses.AdoptionDocDTOResponse;
import br.com.compass.adoptionapi.entities.AdoptionDoc;
import br.com.compass.adoptionapi.repositories.AdoptionDocRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class AdoptionDocServiceImplTest {

  @Mock
  private AdoptionDocRepository adoptionRepository;

  @Mock
  private PetRepositoryFeignClient petClient;

  @InjectMocks
  private AdoptionDocServiceImpl adoptionService;

  @ParameterizedTest
  @MethodSource("br.com.compass.adoptionapi.dummy.DummyAdoptionDoc#generateDTORequests")
  @DisplayName("SERVICE - must create a new adoption document")
  void mustCreateaPetSerive(AdoptionDocDTORequest request) {
    String petId = request.petId();
    PetDTO pet = new PetDTO(UUID.fromString(petId), "Toto", false);
    doReturn(pet).when(petClient).getPetById(anyString());

    AdoptionDoc adoptionDoc = new AdoptionDoc(UUID.randomUUID(), UUID.fromString(request.petId()),
      request.tutorName(), Instant.now(), null);

    doReturn(adoptionDoc).when(adoptionRepository).save(any(AdoptionDoc.class));

    AdoptionDocDTOResponse response = adoptionService.create(request);
    assertAll( () -> {
      assertEquals(request.petId(), response.petId().toString());
        assertEquals(request.tutorName(), response.tutorName());
        assertEquals(adoptionDoc.getId(), response.id());
    });
    verify(adoptionRepository, times(1)).save(any(AdoptionDoc.class));
    verify(petClient, times(1)).getPetById(anyString());
  }
  @ParameterizedTest
  @MethodSource("br.com.compass.adoptionapi.dummy.DummyAdoptionDoc#generateDTORequests")
  @DisplayName("SERVICE - must return all documents")
  void mustFindAllDocuments(AdoptionDocDTORequest request) {
    AdoptionDoc adoptionDoc = new AdoptionDoc(UUID.randomUUID(), UUID.fromString(request.petId()),
            request.tutorName(), Instant.now(), null);
    doReturn(List.of(adoptionDoc)).when(adoptionRepository).findAll();
    List<AdoptionDocDTOResponse> adoptionDocs = adoptionService.findAll();
    assertEqualsMethodList(request, adoptionDocs);
    verify(adoptionRepository, times(1)).findAll();
  }

  @ParameterizedTest
  @MethodSource("br.com.compass.adoptionapi.dummy.DummyAdoptionDoc#generateDTORequests")
  @DisplayName("SERVICE - find adoption document by id")
  void mustFindAdoptionDocById() {
    UUID adoptionDocId = UUID.randomUUID();

    AdoptionDoc dummyAdoptionDoc = new AdoptionDoc(
            adoptionDocId,
            UUID.randomUUID(),
            "Dummy Tutor",
            Instant.now(),
            null
    );

    doReturn(Optional.of(dummyAdoptionDoc)).when(adoptionRepository).findById(any(UUID.class));

    AdoptionDocDTOResponse response = adoptionService.findById(adoptionDocId.toString());

    assertAll(() -> {
      assertNotNull(response);
      assertEquals(adoptionDocId, response.id());
    });

    verify(adoptionRepository, times(1)).findById(any(UUID.class));
  }

  private void assertEqualsMethodList(AdoptionDocDTORequest request, List<AdoptionDocDTOResponse> adoptionDocs) {
      adoptionDocs.forEach(adoptionDoc -> assertEqualsMethod(request, adoptionDoc));
  }

  private void assertEqualsMethod(AdoptionDocDTORequest request, AdoptionDocDTOResponse adoptionDoc) {
    assertAll(
            () -> assertEquals(request.petId(), adoptionDoc.petId().toString()),
            () -> assertEquals(request.tutorName(), adoptionDoc.tutorName())
    );
  }
}

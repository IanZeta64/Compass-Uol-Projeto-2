package br.com.compass.adoptionapi.services;
import br.com.compass.adoptionapi.clients.dto.PetDTO;
import br.com.compass.adoptionapi.clients.repositories.PetFeignClient;
import br.com.compass.adoptionapi.dto.requests.AdoptionDocDTORequest;
import br.com.compass.adoptionapi.dto.responses.AdoptionDocDTOResponse;
import br.com.compass.adoptionapi.dummy.DummyAdoptionDoc;
import br.com.compass.adoptionapi.entities.AdoptionDoc;
import br.com.compass.adoptionapi.exceptions.AdoptionDocNotFoundException;
import br.com.compass.adoptionapi.exceptions.PetAlreadyAdoptedException;
import br.com.compass.adoptionapi.repositories.AdoptionDocRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
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
  private PetFeignClient petClient;

  @InjectMocks
  private AdoptionDocServiceImpl adoptionService;

  @ParameterizedTest
  @MethodSource("br.com.compass.adoptionapi.dummy.DummyAdoptionDoc#generateDTORequests")
  @DisplayName("SERVICE - must create a new adoption document")
  void mustCreateaPetService(AdoptionDocDTORequest request) {
    PetDTO pet = DummyAdoptionDoc.returnPetDTOFromRequestPetId(request.petId());
    doReturn(pet).when(petClient).getPetById(anyString());

    AdoptionDoc adoptionDoc = DummyAdoptionDoc.returnEntitiesFromRequest(request);

    doReturn(adoptionDoc).when(adoptionRepository).save(any(AdoptionDoc.class));

    AdoptionDocDTOResponse response = adoptionService.create(request);
    assertAll( () -> {
        assertEqualsMethod(request, response);
        assertEquals(adoptionDoc.getId(), response.id());
        assertEquals(pet, response.pet());
        assertTrue(response.pet().getIsAdopted());
        assertDoesNotThrow(() -> new PetAlreadyAdoptedException("Pet already adopted"), "Pet already adopted");
    });
    verify(adoptionRepository, times(1)).save(any(AdoptionDoc.class));
    verify(petClient, times(1)).getPetById(anyString());
  }

  @ParameterizedTest
  @MethodSource("br.com.compass.adoptionapi.dummy.DummyAdoptionDoc#generateDTORequests")
  @DisplayName("SERVICE - Throws exception on create a new adoption document")
  void mustThrowExceptionOnCreateAdoptionDocumentMethod(AdoptionDocDTORequest request) {
    PetDTO pet = DummyAdoptionDoc.returnAdoptedPetDTOFromRequestPetId(request.petId());
    doReturn(pet).when(petClient).getPetById(anyString());

    assertTrue(pet.getIsAdopted());
    assertThrows(PetAlreadyAdoptedException.class, () -> adoptionService.create(request));
    verify(petClient, times(1)).getPetById(anyString());
  }

  @ParameterizedTest
  @MethodSource("br.com.compass.adoptionapi.dummy.DummyAdoptionDoc#generateDTORequests")
  @DisplayName("SERVICE - must return all documents")
  void mustFindAllDocuments(AdoptionDocDTORequest request) {
    AdoptionDoc adoptionDoc = DummyAdoptionDoc.returnEntitiesFromRequest(request);
    PetDTO pet = DummyAdoptionDoc.returnPetDTOFromRequestPetId(request.petId());
    doReturn(pet).when(petClient).getPetById(anyString());
    doReturn(List.of(adoptionDoc)).when(adoptionRepository).findAll();
    List<AdoptionDocDTOResponse> adoptionDocs = adoptionService.findAll();
    assertEqualsMethodList(request, adoptionDocs);
    verify(adoptionRepository, times(1)).findAll();
    verify(petClient, times(1)).getPetById(anyString());
  }

  @ParameterizedTest
  @MethodSource("br.com.compass.adoptionapi.dummy.DummyAdoptionDoc#generateDTORequests")
  @DisplayName("SERVICE - find adoption document by id")
  void mustFindAdoptionDocById(AdoptionDocDTORequest request) {
    AdoptionDoc adoptionDoc = DummyAdoptionDoc.returnEntitiesFromRequest(request);
    PetDTO pet = DummyAdoptionDoc.returnPetDTOFromRequestPetId(request.petId());

    String errorMessage = String.format("Adoption document not founded by id %s.", adoptionDoc.getId());

    doReturn(pet).when(petClient).getPetById(anyString());
    doReturn(Optional.of(adoptionDoc)).when(adoptionRepository).findById(any(UUID.class));

    AdoptionDocDTOResponse response = adoptionService.findById(adoptionDoc.getId().toString());

    assertAll( () -> {
      assertEqualsMethod(request, response);
      assertEquals(adoptionDoc.getId(), response.id());
      assertEquals(pet, response.pet());
      assertDoesNotThrow(() -> new AdoptionDocNotFoundException(errorMessage), errorMessage);
    });

    verify(adoptionRepository, times(1)).findById(any(UUID.class));
    verify(petClient, times(1)).getPetById(anyString());
  }

  @ParameterizedTest
  @MethodSource("br.com.compass.adoptionapi.dummy.DummyAdoptionDoc#generateDTORequests")
  @DisplayName("SERVICE - delete adoption document by id")
  void mustDeleteAdoptionDocById(AdoptionDocDTORequest request) {
    AdoptionDoc adoptionDoc = DummyAdoptionDoc.returnEntitiesFromRequest(request);
    String errorMessage = String.format("Can't delete document by id %s.", adoptionDoc.getId());

    doReturn(Optional.of(adoptionDoc)).when(adoptionRepository).findById(any(UUID.class));
    doNothing().when(adoptionRepository).delete(any(AdoptionDoc.class));

    adoptionService.delete(adoptionDoc.getId().toString());

    assertDoesNotThrow(() -> new AdoptionDocNotFoundException(errorMessage), errorMessage);

    verify(adoptionRepository, times(1)).findById(any(UUID.class));
  }

  @ParameterizedTest
  @MethodSource("br.com.compass.adoptionapi.dummy.DummyAdoptionDoc#generateId")
  @DisplayName("SERVICE - throw adoption document by id when delete")
  void mustReturnAdoptionDocNotFoundExceptionWhenDelete(String id) {
    Optional<AdoptionDoc> optional = Optional.empty();
    assertFalse(optional.isPresent());
    doReturn(optional).when(adoptionRepository).findById(any(UUID.class));

    assertThrows(AdoptionDocNotFoundException.class,() -> adoptionService.delete(id));
    verify(adoptionRepository, times(1)).findById(any(UUID.class));

  }



  private void assertEqualsMethodList(AdoptionDocDTORequest request, List<AdoptionDocDTOResponse> adoptionDocs) {
      adoptionDocs.forEach(adoptionDoc -> assertEqualsMethod(request, adoptionDoc));
  }

  private void assertEqualsMethod(AdoptionDocDTORequest request, AdoptionDocDTOResponse adoptionDoc) {
    assertAll(
            () -> assertEquals(request.petId(), adoptionDoc.pet().getId().toString()),
            () -> assertEquals(request.tutorName(), adoptionDoc.tutorName())
    );
  }
}

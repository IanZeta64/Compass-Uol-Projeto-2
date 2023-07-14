package br.com.compass.petapi.services;
import br.com.compass.petapi.dto.reponses.PetDTOResponse;
import br.com.compass.petapi.dto.requests.PetDTORequest;
import br.com.compass.petapi.entities.Pet;
import br.com.compass.petapi.enums.Gender;
import br.com.compass.petapi.enums.Specie;
import br.com.compass.petapi.exceptions.DuplicatedPetException;
import br.com.compass.petapi.exceptions.PetNotFoundException;
import br.com.compass.petapi.repositories.PetRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class PetServiceImplTest {
    @Mock
    private PetRepository petRepository;

    @InjectMocks
    private PetServiceImpl petService;


    @ParameterizedTest
    @MethodSource("br.com.compass.petapi.dummy.DummyPet#generateDTORequests")
    @DisplayName("SERVICE - Create a new pet")
    void mustCreatePetMethod(PetDTORequest request) {
        Pet pet = returnPetWithIdFromRequest(request);

        doReturn(pet).when(petRepository).save(Mockito.any(Pet.class));
        doReturn(false).when(petRepository).existsByNameAndBirthDate(anyString(), any());
        PetDTOResponse response = petService.create(request);

        assertEqualsMethod(request, response);
        assertEquals(pet.getId(), response.id());
        assertDoesNotThrow(() -> DuplicateFormatFlagsException.class,
          String.format("A pet with name %s and birth date %s already exists.",
          request.name(), request.birthDate()));
        verify(petRepository).save(any(Pet.class));
        verify(petRepository).existsByNameAndBirthDate(anyString(), any());
    }

  @ParameterizedTest
  @MethodSource("br.com.compass.petapi.dummy.DummyPet#generateDTORequests")
  @DisplayName("SERVICE - Throws exception on create a new pet")
  void mustThrowExceptionOnCreatePetMethod(PetDTORequest request) {

     doReturn(true).when(petRepository).existsByNameAndBirthDate(anyString(), any());
     assertThrows(DuplicatedPetException.class, () -> petService.create(request));
     verify(petRepository).existsByNameAndBirthDate(anyString(), any());
  }

    @ParameterizedTest
    @MethodSource("br.com.compass.petapi.dummy.DummyPet#generateDTORequests")
    @DisplayName("SERVICE - Search by name")
    void mustSearchByNamePetMethod(PetDTORequest request){
        Pet pet = new Pet(request);
        doReturn(List.of(pet)).when(petRepository).findByNameIgnoreCaseContaining(any(String.class));
        List<PetDTOResponse> responseList = petService.searchByName(pet.getName().substring(0, 2));
        assertEqualsMethodList(request, responseList);

      verify(petRepository).findByNameIgnoreCaseContaining(any(String.class));
    }

  @ParameterizedTest
  @MethodSource("br.com.compass.petapi.dummy.DummyPet#generateDTORequests")
  @DisplayName("SERVICE - Find pet by id")
  void mustFindPetById(PetDTORequest request){
    Pet pet = returnPetWithIdFromRequest(request);

    doReturn(Optional.of(pet)).when(petRepository).findById(any(UUID.class));
    PetDTOResponse response = petService.getById(pet.getId().toString());
    assertEquals(pet.getId(), response.id());
    assertEqualsMethod(request, response);
    assertDoesNotThrow(() -> PetNotFoundException.class, String.format("Pet not founded by id %s.", pet.getId()));

    verify(petRepository).findById(any(UUID.class));
  }

  @ParameterizedTest
  @MethodSource("br.com.compass.petapi.dummy.DummyPet#generateDTORequests")
  @DisplayName("SERVICE - Throws exceptions find pet by id")
  void mustThrowExceptionsOnFindPetById(PetDTORequest request){
    String id = returnPetWithIdFromRequest(request).getId().toString();
    doThrow(PetNotFoundException.class).when(petRepository).findById(any(UUID.class));
    assertThrows(PetNotFoundException.class, () -> petService.getById(id));

    verify(petRepository).findById(any(UUID.class));
  }

  @ParameterizedTest
  @MethodSource("br.com.compass.petapi.dummy.DummyPet#generateDTORequests")
  @DisplayName("SERVICE - update pet")
  void mustUpdatePet(PetDTORequest request) {
    Pet pet = returnPetWithIdFromRequest(request);

    PetDTORequest petToGetNewInformations = new PetDTORequest("Goku", "MALE", "DOG", LocalDate.of(2010,12,31));

    Pet petUpdated = new Pet(
      pet.getId(),
      petToGetNewInformations.name(),
      Gender.valueOf(petToGetNewInformations.gender()),
      Specie.valueOf(petToGetNewInformations.specie()),
      pet.getIsAdopted(),
      petToGetNewInformations.birthDate(),
      pet.getRegisterOn(),
      Instant.now());

    doReturn(Optional.of(pet)).when(petRepository).findById(any(UUID.class));
    doReturn(petUpdated).when(petRepository).save(any(Pet.class));

    PetDTOResponse response = petService.update(pet.getId().toString(), petToGetNewInformations);

    assertEqualsMethod(petToGetNewInformations, response);
    assertEquals(pet.getId(), response.id());
    assertDoesNotThrow(() -> PetNotFoundException.class, String.format("Pet not founded by id %s. Cannot update pet.", pet.getId()));

    verify(petRepository).save(any(Pet.class));
    verify(petRepository).findById(any(UUID.class));
  }

  @ParameterizedTest
  @MethodSource("br.com.compass.petapi.dummy.DummyPet#generateDTORequests")
  @DisplayName("SERVICE - Throw exception update pet")
  void mustThrowExceptionUpdatePet(PetDTORequest request) {
    String id = returnPetWithIdFromRequest(request).getId().toString();
    PetDTORequest petToGetNewInformations = new PetDTORequest("Goku", "MALE", "DOG", LocalDate.of(2010,12,31));
    doThrow(PetNotFoundException.class).when(petRepository).findById(any(UUID.class));
    assertThrows(PetNotFoundException.class,
    () -> petService.update(id, petToGetNewInformations));

    verify(petRepository).findById(any(UUID.class));
  }
    @ParameterizedTest
    @MethodSource("br.com.compass.petapi.dummy.DummyPet#generateDTORequests")
    @DisplayName("Test find all")
    void mustTestFindAllMethod(PetDTORequest request){
        Pet pet = new Pet(request);
        doReturn(List.of(pet)).when(petRepository).findAll();
        List<PetDTOResponse> responseList = petService.findAll();
        assertEqualsMethodList(request, responseList);
        verify(petRepository).findAll();
    }

  @ParameterizedTest
  @MethodSource("br.com.compass.petapi.dummy.DummyPet#generateDTORequests")
  @DisplayName("Test find all not adopted")
  void mustTestFindAllNotAdoptedMethod(PetDTORequest request){
    Pet pet = new Pet(request);
    doReturn(List.of(pet)).when(petRepository).findAllByIsAdoptedFalse();
    List<PetDTOResponse> responseList = petService.findAllNotAdopted();
    assertEqualsMethodList(request, responseList);
    assertFalse(responseList.get(0).isAdopted());
    verify(petRepository).findAllByIsAdoptedFalse();
  }


    @ParameterizedTest
    @MethodSource("br.com.compass.petapi.dummy.DummyPet#generateDTORequests")
    @DisplayName("Test delete by ID")
    void mustTestDeleteMethod(PetDTORequest request){
        Pet pet = returnPetWithIdFromRequest(request);
        doReturn(Optional.of(pet)).when(petRepository).findById(any(UUID.class));
        doNothing().when(petRepository).delete(any(Pet.class));
        petService.delete(pet.getId().toString());
        assertDoesNotThrow(() -> PetNotFoundException.class, String.format("Pet not founded by id %s. Cannot delete pet.", pet.getId()));
        verify(petRepository).delete(any(Pet.class));
    }

    @ParameterizedTest
    @MethodSource("br.com.compass.petapi.dummy.DummyPet#generateDTORequests")
    @DisplayName("Test throw exception on Delete Method")
    void mustThrowExceptionOnDeleteMethod(PetDTORequest request){
        Pet pet = returnPetWithIdFromRequest(request);
        doThrow(PetNotFoundException.class).when(petRepository).findById(any(UUID.class));
        assertThrows(PetNotFoundException.class, () -> petService.delete(pet.getId().toString()));
        verify(petRepository).findById(any(UUID.class));
    }
    @ParameterizedTest
    @MethodSource("br.com.compass.petapi.dummy.DummyPet#generateDTORequests")
    @DisplayName("Test patch status")
    void mustTestPatchStatus(PetDTORequest request){
        Pet pet = returnPetWithIdFromRequest(request);
        assertFalse(pet.getIsAdopted());
        doReturn(Optional.of(pet)).when(petRepository).findById(any(UUID.class));
        doReturn(pet).when(petRepository).save(any(Pet.class));
        PetDTOResponse response = petService.patchStatus(pet.getId().toString());
        assertEqualsMethod(request, response);
        assertTrue(response.isAdopted());
        assertDoesNotThrow(() -> PetNotFoundException.class, String.format("Pet not founded by id %s. Cannot delete pet.", pet.getId()));
        verify(petRepository).findById(any(UUID.class));
        verify(petRepository).save(any(Pet.class));
    }
    @ParameterizedTest
    @MethodSource("br.com.compass.petapi.dummy.DummyPet#generateDTORequests")
    @DisplayName("Test throw Exception patch status")
    void mustThrowExceptionOnPatchStatus(PetDTORequest request){
        String id = returnPetWithIdFromRequest(request).getId().toString();
        doThrow(PetNotFoundException.class).when(petRepository).findById(any(UUID.class));
        assertThrows(PetNotFoundException.class, () -> petService.patchStatus(id));
        verify(petRepository).findById(any(UUID.class));
    }


  private static void assertEqualsMethod(PetDTORequest request, PetDTOResponse response){
    assertAll(
      () -> assertEquals(request.name(), response.name()),
      () -> assertEquals(Gender.valueOf(request.gender()), response.gender()),
      () -> assertEquals(Specie.valueOf(request.specie()), response.specie()),
      () -> assertEquals(request.birthDate(), response.birthDate())
    );
  }
  private static void assertEqualsMethodList(PetDTORequest request, List<PetDTOResponse> responseList){
    responseList.forEach(petResponse -> assertEqualsMethod(request, petResponse));
  }

  private static Pet returnPetWithIdFromRequest(PetDTORequest request){
    return new Pet(UUID.randomUUID(), request.name(), Gender.valueOf(request.gender()),
      Specie.valueOf(request.specie()), false, request.birthDate(), Instant.now(), null);
  }
}

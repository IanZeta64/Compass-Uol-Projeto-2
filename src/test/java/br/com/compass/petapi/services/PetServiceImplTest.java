package br.com.compass.petapi.services;

import br.com.compass.petapi.dto.reponses.PetDTOResponse;
import br.com.compass.petapi.dto.requests.PetDTORequest;
import br.com.compass.petapi.entities.Pet;
import br.com.compass.petapi.enums.Gender;
import br.com.compass.petapi.enums.Specie;
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

import java.time.LocalDate;
import java.util.Optional;
import java.util.*;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class PetServiceImplTest {
    @Mock
    private PetRepository petRepository;

    @InjectMocks
    private PetServiceImpl petService;


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
    private static boolean assertEqualsMethod(PetDTORequest petDTORequest, PetDTOResponse response){
        try{
            assertEquals(petDTORequest.name(), response.name());
            assertEquals(Gender.valueOf(petDTORequest.gender()), response.gender());
            assertEquals(Specie.valueOf(petDTORequest.specie()), response.specie());
            assertEquals(petDTORequest.birthDate(), response.birthDate());
            return true;
        }catch(AssertionError e){
            return false;
        }
    }
    private static boolean assertEqualsMethodList(PetDTORequest petDTORequest, List<PetDTOResponse> response){
            return response.stream()
                    .allMatch(petResponse -> assertEqualsMethod(petDTORequest, petResponse));
    }

    @ParameterizedTest
    @MethodSource("generateDTORequests")
    @DisplayName("Create a new pet by PetDTORequest")
    void mustTestCreatePetMethod(PetDTORequest petDTORequest) {
        doReturn(petDTORequest.toEntity()).when(petRepository).save(Mockito.any(Pet.class));
        PetDTOResponse response = petService.create(petDTORequest);
        assertTrue(assertEqualsMethod(petDTORequest, response));
    }

    @ParameterizedTest
    @MethodSource("generateDTORequests")
    @DisplayName("Search by name")
    void mustTestSearchByNamePetMethod(PetDTORequest petDTORequest){
        Pet pet = petDTORequest.toEntity();
        List<Pet> petList = Collections.singletonList(pet);
        doReturn(petList).when(petRepository).findByNameIgnoreCaseContaining(Mockito.any(String.class));
        List<PetDTOResponse> response = petService.searchByName(pet.getName());
        assertTrue(assertEqualsMethodList(petDTORequest, response));
    }
}
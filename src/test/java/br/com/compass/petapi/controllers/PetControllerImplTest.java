package br.com.compass.petapi.controllers;

import br.com.compass.petapi.dto.reponses.PetDTOResponse;
import br.com.compass.petapi.dto.requests.PetDTORequest;
import br.com.compass.petapi.entities.Pet;
import br.com.compass.petapi.enums.Gender;
import br.com.compass.petapi.enums.Specie;
import br.com.compass.petapi.services.PetServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class PetControllerImplTest {

    @InjectMocks
    private PetControllerImpl controller;

    @Mock
    private PetServiceImpl petService;
    private MockMvc mockMvc;
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        this.mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @ParameterizedTest
    @MethodSource("generateDTORequests")
    @DisplayName("Test for create's controller")
    void mustTestCreateOnController(PetDTORequest request) throws Exception {
        PetDTOResponse response = returnPetDTOResponseFromRequest(request);
        doReturn(response).when(petService).create(any(PetDTORequest.class));
        String requestJson = this.mapper.writeValueAsString(request);
        this.mockMvc.perform(post("/api/v1/pet").
                contentType(MediaType.APPLICATION_JSON).content(requestJson)).
                andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(request.name()));
        verify(petService).create(any(PetDTORequest.class));
    }

    @ParameterizedTest
    @MethodSource("generateDTORequests")
    @DisplayName("Test for findAll's controller")
    void mustTestFindAllOnController(PetDTORequest request) throws Exception {
        PetDTOResponse response = returnPetDTOResponseFromRequest(request);
        List<PetDTOResponse> listResponse = List.of(response);
        doReturn(listResponse).when(petService).findAll();
        this.mockMvc.perform(get("/api/v1/pet")).
                andDo(print()).andExpect(status().isOk()).
                andExpect(jsonPath("$.[0].name").value(request.name()));
        verify(petService).findAll();
    }
    @ParameterizedTest
    @MethodSource("generateDTORequests")
    @DisplayName("Test for getById's controller")
    void mustTestGetByIdOnController(PetDTORequest request) throws Exception {
        PetDTOResponse response = returnPetDTOResponseFromRequest(request);
        doReturn(response).when(petService).getById(anyString());
        this.mockMvc.perform(get("/api/v1/pet/{id}", response.id())).
                andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(request.name()));
        verify(petService).getById(anyString());
    }

    @ParameterizedTest
    @MethodSource("generateDTORequests")
    @DisplayName("Test for getById's controller")
    void mustTestSearchByNameOnController(PetDTORequest request) throws Exception {
        PetDTOResponse response = returnPetDTOResponseFromRequest(request);
        doReturn(List.of(response)).when(petService).searchByName(anyString());
        this.mockMvc.perform(get("/api/v1/pet").param("name", request.name().substring(0,2))).
                andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name").value(request.name()));
        verify(petService).searchByName(anyString());
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

    private static PetDTOResponse returnPetDTOResponseFromRequest(PetDTORequest petDTORequest){
        return new PetDTOResponse(UUID.randomUUID(), petDTORequest.name(), Gender.valueOf(petDTORequest.gender()),
                Specie.valueOf(petDTORequest.specie()), false, petDTORequest.birthDate());
    }



}
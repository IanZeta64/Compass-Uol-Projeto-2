package br.com.compass.petapi.controllers;
import br.com.compass.petapi.dto.reponses.PetDTOResponse;
import br.com.compass.petapi.dto.requests.PetDTORequest;
import br.com.compass.petapi.dummy.DummyPet;
import br.com.compass.petapi.enums.Gender;
import br.com.compass.petapi.enums.Specie;
import br.com.compass.petapi.services.PetServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    @MethodSource("br.com.compass.petapi.dummy.DummyPet#generateDTORequests")
    @DisplayName("CONTROLLER - Test for create's controller")
    void mustTestCreateOnController(PetDTORequest request) throws Exception {
        PetDTOResponse response = DummyPet.returnResponseFromRequest(request);
        doReturn(response).when(petService).create(any(PetDTORequest.class));
        String requestJson = this.mapper.writeValueAsString(request);
        this.mockMvc.perform(post("/api/v1/pet").
                contentType(MediaType.APPLICATION_JSON).content(requestJson)).
                andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(request.name()));
        verify(petService, times(1)).create(any(PetDTORequest.class));
    }

  @ParameterizedTest
  @MethodSource("br.com.compass.petapi.dummy.DummyPet#generateBadDTORequests")
  @DisplayName("CONTROLLER - Must return bad request on create pet")
  void mustReturnBadRequest(PetDTORequest invalidRequest) throws Exception {
    String invalidRequestjson = mapper.writeValueAsString(invalidRequest);

    this.mockMvc.perform(post("/api/v1/pet")
        .contentType(MediaType.APPLICATION_JSON).content(invalidRequestjson))
      .andExpect(status().isBadRequest());
    verify(petService, times(0)).create(any(PetDTORequest.class));

  }

    @ParameterizedTest
    @MethodSource("br.com.compass.petapi.dummy.DummyPet#generateDTORequests")
    @DisplayName("CONTROLLER - Test for findAll's controller")
    void mustTestFindAllOnController(PetDTORequest request) throws Exception {
        PetDTOResponse response = DummyPet.returnResponseFromRequest(request);
        List<PetDTOResponse> listResponse = List.of(response);
        doReturn(listResponse).when(petService).findAll();
        this.mockMvc.perform(get("/api/v1/pet")).
                andDo(print()).andExpect(status().isOk()).
                andExpect(jsonPath("$.[0].name").value(request.name()));
        verify(petService, times(1)).findAll();
    }

  @ParameterizedTest
  @MethodSource("br.com.compass.petapi.dummy.DummyPet#generateDTORequests")
  @DisplayName("CONTROLLER - Test for findAll pets not adopted")
  void mustTestFindAllNotAdoptedOnController(PetDTORequest request) throws Exception {
    PetDTOResponse response = DummyPet.returnResponseFromRequest(request);
    List<PetDTOResponse> listResponse = List.of(response);
    doReturn(listResponse).when(petService).findAllNotAdopted();
    this.mockMvc.perform(get("/api/v1/pet/notAdopted")).
      andDo(print()).andExpect(status().isOk()).
      andExpect(jsonPath("$.[0].isAdopted").value(false));
    verify(petService, times(1)).findAllNotAdopted();
  }

    @ParameterizedTest
    @MethodSource("br.com.compass.petapi.dummy.DummyPet#generateDTORequests")
    @DisplayName("CONTROLLER - Test for getById's controller")
    void mustTestGetByIdOnController(PetDTORequest request) throws Exception {

        PetDTOResponse response = DummyPet.returnResponseFromRequest(request);
        doReturn(response).when(petService).getById(anyString());
        this.mockMvc.perform(get("/api/v1/pet/{id}", response.id().toString())).
                andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(request.name()));
        verify(petService, times(1)).getById(anyString());
    }

    @ParameterizedTest
    @MethodSource("br.com.compass.petapi.dummy.DummyPet#generateDTORequests")
    @DisplayName("CONTROLLER - Test search pet by part of name")
    void mustTestSearchByNameOnController(PetDTORequest request) throws Exception {
        PetDTOResponse response = DummyPet.returnResponseFromRequest(request);
        doReturn(List.of(response)).when(petService).searchByName(anyString());
        this.mockMvc.perform(get("/api/v1/pet/search").param("name", request.name().substring(0,2))).
                andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name").value(request.name()));
        verify(petService, times(1)).searchByName(anyString());
    }

  @ParameterizedTest
  @MethodSource("br.com.compass.petapi.dummy.DummyPet#generateId")
  @DisplayName("CONTROLLER - Test update pet")
  void mustUpdatePet(String id) throws Exception {

    PetDTORequest requestToUpdate = new PetDTORequest("Goku", "MALE", "DOG", LocalDate.of(2021,10,1));

    PetDTOResponse petUpdated = new PetDTOResponse(UUID.fromString(id), requestToUpdate.name(), Gender.valueOf(requestToUpdate.gender()),
      Specie.valueOf(requestToUpdate.specie()), false, requestToUpdate.birthDate());


    String requestToUpdateJson =  mapper.writeValueAsString(requestToUpdate);
    String petUpdatedJson =  mapper.writeValueAsString(petUpdated);


    doReturn(petUpdated).when(petService).update(anyString(), any(PetDTORequest.class));

    this.mockMvc.perform(put("/api/v1/pet/{id}", id)
        .contentType(MediaType.APPLICATION_JSON).content(requestToUpdateJson))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(content().json(petUpdatedJson));

    verify(petService, times(1)).update(anyString(), any(PetDTORequest.class));
  }

  @ParameterizedTest
  @MethodSource("br.com.compass.petapi.dummy.DummyPet#generateId")
  @DisplayName("CONTROLLER - Delete pet from id")
  void mustDeletePetById(String id) throws Exception {
    doNothing().when(petService).delete(anyString());

    this.mockMvc.perform(delete("/api/v1/pet/{id}", id))
      .andDo(print())
      .andExpect(status().isNoContent());

    verify(petService, times(1)).delete(anyString());
  }

  @ParameterizedTest
  @MethodSource("br.com.compass.petapi.dummy.DummyPet#generateDTORequests")
  @DisplayName("CONTROLLER - patch adopted pet status by id")
  void mustpatchAdoptedPetStatusById(PetDTORequest request) throws Exception {
    var response = DummyPet.returnADoptedPetResponseFromRequest(request);
    doReturn(response).when(petService).patchStatus(anyString());
    this.mockMvc.perform(patch("/api/v1/pet/alterAdoptedStatus/{id}", response.id().toString()))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.isAdopted").value(true));

    verify(petService, times(1)).patchStatus(anyString());
  }

}

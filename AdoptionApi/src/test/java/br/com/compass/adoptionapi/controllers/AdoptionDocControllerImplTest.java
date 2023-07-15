package br.com.compass.adoptionapi.controllers;
import br.com.compass.adoptionapi.clients.dto.PetDTO;
import br.com.compass.adoptionapi.clients.repositories.PetRepositoryFeignClient;
import br.com.compass.adoptionapi.dto.requests.AdoptionDocDTORequest;
import br.com.compass.adoptionapi.dto.responses.AdoptionDocDTOResponse;
import br.com.compass.adoptionapi.dummy.DummyAdoptionDoc;

import br.com.compass.adoptionapi.services.AdoptionDocServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class AdoptionDocControllerImplTest {

  @InjectMocks
  private AdoptionDocControllerImpl controller;
//  @Mock
//  private PetRepositoryFeignClient petClient;
  @Mock
  private AdoptionDocServiceImpl service;
  private MockMvc mockMvc;
  private ObjectMapper mapper;

  @BeforeEach
  void setUp() {
    this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    this.mapper = new ObjectMapper().registerModule(new JavaTimeModule());
  }

  @AfterEach
  void tearDown() {
  }

//
//  @ParameterizedTest
//  @MethodSource("br.com.compass.adoptionapi.dummy.DummyAdoptionDoc#generateDTORequests")
//  @DisplayName("CONTROLLER - Test for create's controller")
//  void mustTestCreateOnController(AdoptionDocDTORequest request) throws Exception {
//
//    AdoptionDocDTOResponse response = DummyAdoptionDoc.returnResponseFromRequest(request);
//    PetDTO petDTO = DummyAdoptionDoc.returnPetDTO(request.petId());
//
////    doReturn(petDTO).when(petClient).getPetById(anyString());
//    doReturn(response).when(service).create(any(AdoptionDocDTORequest.class));
//
//
////    PetDTO pet = petClient.getPetById(request.petId());
//    String requestJson = this.mapper.writeValueAsString(request);
//    this.mockMvc.perform(post("/api/v1/pet").
//        contentType(MediaType.APPLICATION_JSON).content(requestJson)).
//      andDo(print()).andExpect(status().isCreated())
//      .andExpect(jsonPath("$.tutorName").value(request.tutorName()));
//    verify(service, times(1)).create(any(AdoptionDocDTORequest.class));
//  }
}

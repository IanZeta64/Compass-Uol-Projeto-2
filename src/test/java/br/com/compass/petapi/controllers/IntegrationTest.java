package br.com.compass.petapi.controllers;
import br.com.compass.petapi.AdoptionPetApiApplication;
import br.com.compass.petapi.dto.requests.PetDTORequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import java.time.LocalDate;
import java.util.UUID;
import java.util.stream.Stream;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc

@SpringBootTest( classes = AdoptionPetApiApplication.class,
  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class IntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  private ObjectMapper mapper;

  @BeforeEach
  void setUp(){
    this.mapper = new ObjectMapper().registerModule(new JavaTimeModule());
  }

  @ParameterizedTest
  @MethodSource("generateDTORequests")
  @DisplayName("INTEGRATION - must create a new pet and find pet by returned id")
  void mustCreateNewPetAndFindPetByReturnedId(PetDTORequest request) throws Exception {

    String requestJson = mapper.writeValueAsString(request);

    MvcResult result = mockMvc.perform(post("/api/v1/pet")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestJson))
      .andDo(print())
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.name").value(request.name())).andReturn();


    String id = new JSONObject(result.getResponse().getContentAsString()).getString("id");

    mockMvc.perform(get("/api/v1/pet/{id}", id))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.id").value(id));
  }

  @ParameterizedTest
  @MethodSource("generateDTORequests")
  @DisplayName("INTEGRATION - must create a new pet and find all pets")
  void mustCreateNewPetAndFindAll(PetDTORequest request) throws Exception {

    String requestJson = mapper.writeValueAsString(request);

    MvcResult result = mockMvc.perform(post("/api/v1/pet")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestJson))
      .andDo(print())
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.name").value(request.name())).andReturn();


    String id = new JSONObject(result.getResponse().getContentAsString()).getString("id");

    mockMvc.perform(get("/api/v1/pet"))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$").isNotEmpty());
  }

  @ParameterizedTest
  @MethodSource("generateDTORequests")
  @DisplayName("INTEGRATION - must return eror 409 is conflit on create a duplicated pet")
  void mustreturnError409IsconflictOnCreatePetDuplicated(PetDTORequest request) throws Exception {

    String requestJson = mapper.writeValueAsString(request);

    MvcResult result = mockMvc.perform(post("/api/v1/pet")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestJson))
      .andDo(print())
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.name").value(request.name())).andReturn();


    String id = new JSONObject(result.getResponse().getContentAsString()).getString("id");

    mockMvc.perform(get("/api/v1/pet/{id}", id))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.id").value(id));

    mockMvc.perform(post("/api/v1/pet")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestJson))
      .andDo(print())
      .andExpect(status().isConflict());
  }

  @ParameterizedTest
  @MethodSource("generateId")
  @DisplayName("INTEGRATION - Test for find pet by id and return not founded status")
  void mustNotFindPetByIdAndReturnNotFoundedStatus(String id) throws Exception {
    this.mockMvc.perform(get("/api/v1/pet/{id}", id)).
      andDo(print()).andExpect(status().isNotFound());
  }

  @ParameterizedTest
  @MethodSource("generateDTORequests")
  @DisplayName("INTEGRATION - must search pet by part of name")
  void mustSearchPetByPartOfName(PetDTORequest request) throws Exception {

    String requestJson = mapper.writeValueAsString(request);

    MvcResult result = mockMvc.perform(post("/api/v1/pet")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestJson))
      .andDo(print())
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.name").value(request.name())).andReturn();

    mockMvc.perform(get("/api/v1/pet/search").param("name", request.name()))
      .andDo(print()).andExpect(status().isOk())
      .andExpect(jsonPath("$.[0].name").value(request.name()));

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

  public static Stream<Arguments> generateId() {
    return Stream.of(
      Arguments.of(UUID.randomUUID().toString()),
      Arguments.of(UUID.randomUUID().toString()),
      Arguments.of(UUID.randomUUID().toString())
    );
  }


}

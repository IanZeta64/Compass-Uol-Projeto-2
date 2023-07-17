package br.com.compass.adoptionapi.config;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;

import static java.nio.charset.Charset.defaultCharset;
import static org.springframework.util.StreamUtils.copyToString;

public class PetMock {

  public static void getPetbyIdWireMock(WireMockServer mockService) throws IOException {
    mockService.stubFor(WireMock.get(WireMock.urlEqualTo("/api/v1/pet/543fb418-25d4-477c-b66c-b3288e167bc7"))
      .willReturn(WireMock.aResponse()
        .withStatus(HttpStatus.OK.value())
        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
        .withBody(
          copyToString(
            PetMock.class.getClassLoader().getResourceAsStream("src/test/java/br/com/compass/adoptionapi/config/petJson.json"),
            defaultCharset()))));
  }

  public static void patchStatusPetWireMock(WireMockServer mockService) throws IOException {
    mockService.stubFor(WireMock.patch(WireMock.urlEqualTo("/api/v1/pet/alterStatusPet/543fb418-25d4-477c-b66c-b3288e167bc7"))
      .willReturn(WireMock.aResponse()
        .withStatus(HttpStatus.OK.value())));
  }
}

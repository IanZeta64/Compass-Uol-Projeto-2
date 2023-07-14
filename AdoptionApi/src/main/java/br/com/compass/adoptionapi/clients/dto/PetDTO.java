package br.com.compass.adoptionapi.clients.dto;

import br.com.compass.adoptionapi.clients.enums.Gender;
import br.com.compass.adoptionapi.clients.enums.Specie;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.UUID;

public record PetDTO(
  @JsonProperty("id")
  UUID id,
  @JsonProperty("name")
  String name,
  @JsonProperty("gender")
  Gender gender,
  @JsonProperty("specie")
  Specie specie,
  @JsonProperty("isAdoped")
  Boolean isAdopted,
  @JsonProperty("birthDate")
  LocalDate birthDate) {
}

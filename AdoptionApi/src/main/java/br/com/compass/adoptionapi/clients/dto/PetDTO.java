package br.com.compass.adoptionapi.clients.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetDTO {

    @JsonProperty("id") private UUID id;
    @JsonProperty("name") private String name;
    @JsonProperty("isAdopted") private Boolean isAdopted;
    @JsonProperty("birthDate") private LocalDate birthDate;


}

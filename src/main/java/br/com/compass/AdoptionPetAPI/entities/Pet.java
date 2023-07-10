package br.com.compass.AdoptionPetAPI.entities;
import br.com.compass.AdoptionPetAPI.dto.requests.PetDTORequest;
import br.com.compass.AdoptionPetAPI.enums.Gender;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.time.Instant;
import java.time.LocalDate;

@Document
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pet {

  @Id
  @Field("petId")
  private String id;
  private String name;
  private Gender gender;
  private Boolean isAdopted;
  private LocalDate birthDate;
  private Instant registerOn;
  private Instant modifiedOn;
  //private AdoptionDocument document;
  //Tutor tutor;

  public Pet(PetDTORequest petDTORequest) {
    this.name = petDTORequest.name();
    this.gender = petDTORequest.gender();
    this.isAdopted = false;
    this.birthDate = petDTORequest.birthDate();
    this.registerOn = Instant.now();
  }
}

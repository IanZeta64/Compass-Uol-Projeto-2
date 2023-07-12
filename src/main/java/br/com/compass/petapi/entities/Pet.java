package br.com.compass.petapi.entities;
import br.com.compass.petapi.dto.requests.PetDTORequest;
import br.com.compass.petapi.enums.Gender;
import br.com.compass.petapi.enums.Specie;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;
import java.time.LocalDate;

@Document(collection = "pet")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Pet {

  @Id
  private String id;
  private String name;
  private Gender gender;

  private Specie specie;
  private Boolean isAdopted;
  private LocalDate birthDate;
  private Instant registerOn;
  private Instant modifiedOn;
  //private AdoptionDocument document;
  //Tutor tutor;

  public Pet(PetDTORequest petDTORequest) {
    this.name = petDTORequest.name();
    this.gender = petDTORequest.gender();
    this.specie = petDTORequest.specie();
    this.isAdopted = false;
    this.birthDate = petDTORequest.birthDate();
    this.registerOn = Instant.now();
  }

  public void setAdopted() {
    this.isAdopted = !this.isAdopted;
  }
}

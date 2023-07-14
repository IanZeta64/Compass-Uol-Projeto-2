package br.com.compass.petapi.entities;
import br.com.compass.petapi.dto.requests.PetDTORequest;
import br.com.compass.petapi.enums.Gender;
import br.com.compass.petapi.enums.Specie;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name="pet_table")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Pet {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String name;
  private Gender gender;
  private Specie specie;
  private Boolean isAdopted;
  private LocalDate birthDate;
  private Instant registerOn;
  private Instant modifiedOn;

  public Pet(PetDTORequest petDTORequest) {
    this.name = petDTORequest.name();
    this.gender = Gender.valueOf(petDTORequest.gender());
    this.specie = Specie.valueOf(petDTORequest.specie());
    this.isAdopted = false;
    this.birthDate = petDTORequest.birthDate();
    this.registerOn = Instant.now();
  }
  public void setAdopted() {
    this.isAdopted = !this.isAdopted;
    this.modifiedOn = Instant.now();
  }
}

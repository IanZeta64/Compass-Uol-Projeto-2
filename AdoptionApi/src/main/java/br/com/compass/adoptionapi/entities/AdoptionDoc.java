package br.com.compass.adoptionapi.entities;
import br.com.compass.adoptionapi.dto.requests.AdoptionDocDTORequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "adoption_document_table")
public class AdoptionDoc {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  private UUID petId;
  private String tutorName;
  private Instant registeredOn;
  private  Instant modifiedOn;

  public AdoptionDoc(AdoptionDocDTORequest request) {
    this.petId = UUID.fromString(request.petId());
    this.tutorName = request.tutorName();
    this.registeredOn = Instant.now();
  }
}

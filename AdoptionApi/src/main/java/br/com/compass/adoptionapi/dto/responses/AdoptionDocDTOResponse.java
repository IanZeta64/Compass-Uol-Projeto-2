package br.com.compass.adoptionapi.dto.responses;
import br.com.compass.adoptionapi.clients.dto.PetDTO;
import br.com.compass.adoptionapi.entities.AdoptionDoc;
import java.util.UUID;

public record AdoptionDocDTOResponse (UUID id, String tutorName, PetDTO pet) {
  public AdoptionDocDTOResponse(AdoptionDoc adoptionDoc, PetDTO pet) {
    this(adoptionDoc.getId(),adoptionDoc.getTutorName(), pet);
  }
}

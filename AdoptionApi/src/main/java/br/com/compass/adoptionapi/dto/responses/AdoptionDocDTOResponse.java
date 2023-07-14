package br.com.compass.adoptionapi.dto.responses;

import br.com.compass.adoptionapi.entities.AdoptionDoc;

import java.util.UUID;

public record AdoptionDocDTOResponse (UUID id, UUID petId, String tutorName) {
  public AdoptionDocDTOResponse(AdoptionDoc adoptionDoc) {
    this(adoptionDoc.getId(), adoptionDoc.getPetId(),adoptionDoc.getTutorName());
  }
}

package br.com.compass.adoptionapi.dto.requests.responses;

import java.util.UUID;

public record AdoptionDocDTOResponse (UUID id, UUID petId, String tutorName) {
}

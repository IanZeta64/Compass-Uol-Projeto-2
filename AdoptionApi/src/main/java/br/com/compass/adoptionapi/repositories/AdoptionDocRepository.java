package br.com.compass.adoptionapi.repositories;

import br.com.compass.adoptionapi.dto.responses.AdoptionDocDTOResponse;
import br.com.compass.adoptionapi.entities.AdoptionDoc;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AdoptionDocRepository extends JpaRepository<AdoptionDoc, UUID> {
}

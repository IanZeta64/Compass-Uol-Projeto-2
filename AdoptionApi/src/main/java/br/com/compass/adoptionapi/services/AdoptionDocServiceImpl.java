package br.com.compass.adoptionapi.services;
import br.com.compass.adoptionapi.clients.dto.PetDTO;
import br.com.compass.adoptionapi.clients.repositories.PetRepositoryFeignClient;
import br.com.compass.adoptionapi.dto.requests.AdoptionDocDTORequest;
import br.com.compass.adoptionapi.dto.responses.AdoptionDocDTOResponse;
import br.com.compass.adoptionapi.entities.AdoptionDoc;
import br.com.compass.adoptionapi.exceptions.AdoptionDocNotFoundException;
import br.com.compass.adoptionapi.exceptions.PetAlreadyAdoptedException;
import br.com.compass.adoptionapi.repositories.AdoptionDocRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdoptionDocServiceImpl implements AdoptionDocService{

  private final AdoptionDocRepository repository;
  private final PetRepositoryFeignClient petClient;

  @Override
  public AdoptionDocDTOResponse create(AdoptionDocDTORequest request) {
    log.info("SERVICE - searching for the especific pet");
    PetDTO petDTO = petClient.getPetById(request.petId());
    if (Boolean.TRUE.equals(petDTO.getIsAdopted())){
      throw new PetAlreadyAdoptedException("Pet already adopted");
    }
    log.info("SERVICE - saving the new document");
    AdoptionDoc adoptionDoc = repository.save(new AdoptionDoc(request));
    log.info("SERVICE - updating the isAdopted of the pet to true");
    petClient.patchStatusPet(request.petId());
    return new AdoptionDocDTOResponse(adoptionDoc);
  }

  @Override
  public List<AdoptionDocDTOResponse> findAll() {
    log.info("SERVICE - finding all documents");
  return repository.findAll().stream().map(AdoptionDocDTOResponse::new).toList();
  }

  @Override
  public AdoptionDocDTOResponse findById(String id) {
    UUID uuid;
    uuid = UUID.fromString(id);
    log.info("SERVICE - finding pet by id");
    AdoptionDoc adoptionDocReturn = repository.findById(uuid)
            .orElseThrow(() -> new AdoptionDocNotFoundException(String.format("Adoption doc not founded by id %s." + id)));
    return new AdoptionDocDTOResponse(adoptionDocReturn);
  }

  @Override
  public AdoptionDocDTOResponse update(String id, AdoptionDocDTORequest request) {
    log.info("SERVICE - storing the pet based on the request");
    PetDTO petDTO = petClient.getPetById(request.petId());
    log.info("SERVICE - storing the document actual.");
    Optional<AdoptionDoc> adoptionDoc = repository.findById(UUID.fromString(id));
    log.info("SERVICE - updating the document to the new one");
    AdoptionDoc updatedAdoptionDoc = repository.findById(UUID.fromString(id)).map(adopDoc ->
            new AdoptionDoc(adopDoc.getId(), UUID.fromString(request.petId()),request.tutorName(),
                    adopDoc.getRegisteredOn(),
                    Instant.now())).orElseThrow(() -> new IllegalArgumentException(String.format("Document not founded by id %s. Cannot update adoption document.", id)));

    if (Boolean.TRUE.equals(petDTO.getIsAdopted()) && !(id.equals(updatedAdoptionDoc.getId().toString()))){
      System.out.println("passei aqui");
      throw new PetAlreadyAdoptedException("Pet already adopted by someone else");
    }
    if((!(request.petId().equals(adoptionDoc.get().getId().toString()))) && id.equals(adoptionDoc.get().getId().toString())){
        log.info("Updating the old pet of the document to false");
        petClient.patchStatusPet(adoptionDoc.get().getPetId().toString());
    }
    log.info("Updating the new pet to isAdopted true");
    petClient.patchStatusPet(request.petId());
    return new AdoptionDocDTOResponse(repository.save(updatedAdoptionDoc));
  }
  @Override
  public void delete(String id) {
    UUID uuid;
    try {
      uuid = UUID.fromString(id);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Invalid ID format: " + id);
    }

    Optional<AdoptionDoc> adoptionDocOptional = repository.findById(uuid);
    if (adoptionDocOptional.isEmpty()) {
      throw new AdoptionDocNotFoundException("Adoption doc not found by ID " + id);
    }

    AdoptionDoc adoptionDoc = adoptionDocOptional.get();
    log.info("SERVICE - updating pet isAdopted status to false");
    petClient.patchStatusPet(adoptionDoc.getPetId().toString());
    log.info("SERVICE - deleting pet");
    repository.deleteById(uuid);
  }
}

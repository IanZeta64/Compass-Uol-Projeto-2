package br.com.compass.adoptionapi.services;
import br.com.compass.adoptionapi.clients.dto.PetDTO;
import br.com.compass.adoptionapi.clients.repositories.PetRepositoryFeignClient;
import br.com.compass.adoptionapi.dto.requests.AdoptionDocDTORequest;
import br.com.compass.adoptionapi.dto.responses.AdoptionDocDTOResponse;
import br.com.compass.adoptionapi.entities.AdoptionDoc;
import br.com.compass.adoptionapi.exceptions.PetAlreadyAdoptedException;
import br.com.compass.adoptionapi.repositories.AdoptionDocRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdoptionDocServiceImpl implements AdoptionDocService{

  private final AdoptionDocRepository repository;
  private final PetRepositoryFeignClient petClient;

  @Override
  public AdoptionDocDTOResponse create(AdoptionDocDTORequest request) {
    PetDTO petDTO = petClient.getPetById(request.petId());
    if (Boolean.TRUE.equals(petDTO.getIsAdopted())){
      throw new PetAlreadyAdoptedException("Pet already adopted");
    }
    AdoptionDoc adoptionDoc = repository.save(new AdoptionDoc(request));
    petClient.patchStatusPet(request.petId());
    return new AdoptionDocDTOResponse(adoptionDoc);
  }

  @Override
  public List<AdoptionDocDTOResponse> findAll() {
  return repository.findAll().stream().map(AdoptionDocDTOResponse::new).toList();
  }

  @Override
  public AdoptionDocDTOResponse findById(String id) {
    return null;
  }

  @Override
  public AdoptionDocDTOResponse update(String id, AdoptionDocDTORequest request) {
    PetDTO petDTO = petClient.getPetById(request.petId());
    Optional<AdoptionDoc> adoptionDoc = repository.findById(UUID.fromString(id));
    AdoptionDoc updatedAdoptionDoc = repository.findById(UUID.fromString(id)).map(adopDoc ->
            new AdoptionDoc(adopDoc.getId(), UUID.fromString(request.petId()),request.tutorName(), adopDoc.getRegisteredOn(),
                    Instant.now())).orElseThrow(() -> new IllegalArgumentException(String.format("Document not founded by id %s. Cannot update adoption document.", id)));

    if (Boolean.TRUE.equals(petDTO.getIsAdopted()) && !(id.equals(updatedAdoptionDoc.getId().toString()))){
      throw new PetAlreadyAdoptedException("Pet already adopted by someone else");
    }
    if(!(request.petId().equals(adoptionDoc.get().getId().toString())) && id.equals(adoptionDoc.get().getId().toString())){
      petClient.patchStatusPet(petDTO.getId().toString());
    }

    petClient.patchStatusPet(request.petId());

    return new AdoptionDocDTOResponse(repository.save(updatedAdoptionDoc));
  }

  @Override
  public void delete(String id) {

  }
}

package br.com.compass.adoptionapi.services;
import br.com.compass.adoptionapi.clients.dto.PetDTO;
import br.com.compass.adoptionapi.clients.repositories.PetFeignClient;
import br.com.compass.adoptionapi.dto.requests.AdoptionDocDTORequest;
import br.com.compass.adoptionapi.dto.responses.AdoptionDocDTOResponse;
import br.com.compass.adoptionapi.entities.AdoptionDoc;
import br.com.compass.adoptionapi.exceptions.AdoptionDocNotFoundException;
import br.com.compass.adoptionapi.exceptions.PetAlreadyAdoptedException;
import br.com.compass.adoptionapi.repositories.AdoptionDocRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdoptionDocServiceImpl implements AdoptionDocService{

  private final AdoptionDocRepository repository;
  private final PetFeignClient petClient;

  @Override
  public AdoptionDocDTOResponse create(AdoptionDocDTORequest request) {
    log.info("SERVICE - searching for the especific pet");
    PetDTO petDTO = petClient.getPetById(request.petId());
    if (Boolean.TRUE.equals(petDTO.getIsAdopted())){
      throw new PetAlreadyAdoptedException(String.format("Pet with id %s already adopted", petDTO.getId()));
    }
    log.info("SERVICE - saving the new document");
    AdoptionDoc adoptionDoc = repository.save(new AdoptionDoc(request));
    log.info("SERVICE - updating the isAdopted of the pet to true");
    petClient.patchStatusPet(request.petId());
    petDTO.setIsAdopted(true);
    return new AdoptionDocDTOResponse(adoptionDoc, petDTO);
  }

  @Override
  public List<AdoptionDocDTOResponse> findAll() {
    log.info("SERVICE - finding all documents");
  return repository.findAll().stream().map(doc ->
    new AdoptionDocDTOResponse(doc, petClient.getPetById(doc.getPetId().toString()))
  ).toList();
  }

  @Override
  public AdoptionDocDTOResponse findById(String id) {
    log.info("SERVICE - finding pet by id");
    return repository.findById(UUID.fromString(id)).map(doc ->
      new AdoptionDocDTOResponse(doc, petClient.getPetById(doc.getPetId().toString())))
            .orElseThrow(() -> new AdoptionDocNotFoundException(String.format("Adoption document not founded by id %s.", id)));
  }
  @Override
  public void delete(String id) {
    repository.findById(UUID.fromString(id)).ifPresentOrElse(
            doc -> {
              log.info("SERVICE - deleting pet");
              repository.delete(doc);
              log.info("SERVICE - updating pet isAdopted status to false");
              petClient.patchStatusPet(doc.getPetId().toString());
            },
            () -> {
              throw new AdoptionDocNotFoundException(String.format("Can't delete document by id %s.", id));
            }
    );
  }
}

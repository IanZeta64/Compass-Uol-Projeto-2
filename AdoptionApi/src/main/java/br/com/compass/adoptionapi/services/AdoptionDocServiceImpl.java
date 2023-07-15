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
import java.util.List;

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
    return null;
  }

  @Override
  public AdoptionDocDTOResponse findById(String id) {
    return null;
  }

  @Override
  public AdoptionDocDTOResponse update(String id, AdoptionDocDTORequest request) {
    return null;
  }

  @Override
  public void delete(String id) {

  }
}

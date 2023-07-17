package br.com.compass.adoptionapi.services;
import br.com.compass.adoptionapi.dto.requests.AdoptionDocDTORequest;
import br.com.compass.adoptionapi.dto.responses.AdoptionDocDTOResponse;
import java.util.List;

public interface AdoptionDocService {
   AdoptionDocDTOResponse create(AdoptionDocDTORequest request);
   List<AdoptionDocDTOResponse> findAll();
   AdoptionDocDTOResponse findById( String id);
   void delete( String id);
}

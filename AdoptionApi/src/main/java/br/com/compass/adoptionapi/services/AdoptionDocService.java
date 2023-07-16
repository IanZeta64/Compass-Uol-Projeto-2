package br.com.compass.adoptionapi.services;
import br.com.compass.adoptionapi.dto.requests.AdoptionDocDTORequest;
import br.com.compass.adoptionapi.dto.responses.AdoptionDocDTOResponse;

import java.util.List;

public interface AdoptionDocService {
  public AdoptionDocDTOResponse create(AdoptionDocDTORequest request);

  public List<AdoptionDocDTOResponse> findAll();


  public AdoptionDocDTOResponse findById( String id);


  public AdoptionDocDTOResponse update(String id, AdoptionDocDTORequest request);


  public void delete( String id);
}

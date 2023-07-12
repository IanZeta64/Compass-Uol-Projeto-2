package br.com.compass.petapi.services;

import br.com.compass.petapi.dto.reponses.PetDTOResponse;
import br.com.compass.petapi.dto.requests.PetDTORequest;


import java.util.List;

public interface PetService {

  public PetDTOResponse create(PetDTORequest request);

  public List<PetDTOResponse> findAll();

  public PetDTOResponse getById(String id);

  public List<PetDTOResponse> searchByName(String name);

  public PetDTOResponse update(String id, PetDTORequest request);

  public void delete(String id);

  PetDTOResponse patchStatus(String id);
}

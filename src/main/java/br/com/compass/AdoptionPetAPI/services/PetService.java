package br.com.compass.AdoptionPetAPI.services;

import br.com.compass.AdoptionPetAPI.dto.reponses.PetDTOResponse;
import br.com.compass.AdoptionPetAPI.dto.requests.PetDTORequest;

import java.util.List;

public interface PetService {

  public PetDTOResponse create(PetDTORequest petDTORequest);

  public List<PetDTOResponse> findAll();

  public PetDTOResponse getById(String id);

  public PetDTOResponse searchByName(PetDTORequest petDTORequest);

  public PetDTOResponse update(String id, PetDTORequest petDTORequest);

  public void delete(String id);
}

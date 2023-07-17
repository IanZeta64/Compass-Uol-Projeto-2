package br.com.compass.petapi.services;
import br.com.compass.petapi.dto.reponses.PetDTOResponse;
import br.com.compass.petapi.dto.requests.PetDTORequest;


import java.util.List;

public interface PetService {

  PetDTOResponse create(PetDTORequest request);

 List<PetDTOResponse> findAll();

  PetDTOResponse getById(String id);

  List<PetDTOResponse> searchByName(String name);

  PetDTOResponse update(String id, PetDTORequest request);

  void delete(String id);

  PetDTOResponse patchStatus(String id);

  List<PetDTOResponse> findAllNotAdopted();
}

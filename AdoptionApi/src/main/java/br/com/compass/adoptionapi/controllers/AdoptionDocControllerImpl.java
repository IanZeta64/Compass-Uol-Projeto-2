package br.com.compass.adoptionapi.controllers;

import br.com.compass.adoptionapi.dto.requests.AdoptionDocDTORequest;
import br.com.compass.adoptionapi.dto.responses.AdoptionDocDTOResponse;
import br.com.compass.adoptionapi.services.AdoptionDocService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdoptionDocControllerImpl implements AdoptionDocController{

  private final AdoptionDocService service;
  @Override
  public ResponseEntity<AdoptionDocDTOResponse> create(AdoptionDocDTORequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
  }

  @Override
  public ResponseEntity<List<AdoptionDocDTOResponse>> findAll() {
    return null;
  }

  @Override
  public ResponseEntity<AdoptionDocDTOResponse> findById(String id) {
    return null;
  }

  @Override
  public ResponseEntity<AdoptionDocDTOResponse> update(String id, AdoptionDocDTORequest request) {
    return null;
  }

  @Override
  public ResponseEntity<Void> delete(String id) {
    return null;
  }
}

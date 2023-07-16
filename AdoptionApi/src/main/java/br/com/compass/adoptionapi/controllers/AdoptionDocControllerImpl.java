package br.com.compass.adoptionapi.controllers;

import br.com.compass.adoptionapi.dto.requests.AdoptionDocDTORequest;
import br.com.compass.adoptionapi.dto.responses.AdoptionDocDTOResponse;
import br.com.compass.adoptionapi.exceptions.AdoptionDocNotFoundException;
import br.com.compass.adoptionapi.services.AdoptionDocService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdoptionDocControllerImpl implements AdoptionDocController {

  private final AdoptionDocService service;

  @Override
  public ResponseEntity<AdoptionDocDTOResponse> create(AdoptionDocDTORequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
  }

  @Override
  public ResponseEntity<List<AdoptionDocDTOResponse>> findAll() {
    return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
  }

  @Override
  public ResponseEntity<AdoptionDocDTOResponse> findById(@PathVariable String id) {
    try {
      AdoptionDocDTOResponse adoptionDoc = service.findById(id);
      return ResponseEntity.status(HttpStatus.OK).body(adoptionDoc);
    } catch (AdoptionDocNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

  @Override
  public ResponseEntity<AdoptionDocDTOResponse> update(String id, AdoptionDocDTORequest request) {
    return ResponseEntity.status(HttpStatus.OK).body(service.update(id, request));
  }

  @Override
  public ResponseEntity<Void> delete(String id) {
    try {
      service.delete(id);
      return ResponseEntity.noContent().build();
    } catch (AdoptionDocNotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }
}

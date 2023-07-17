package br.com.compass.adoptionapi.controllers;
import br.com.compass.adoptionapi.dto.requests.AdoptionDocDTORequest;
import br.com.compass.adoptionapi.dto.responses.AdoptionDocDTOResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/adoption")
public interface AdoptionDocController {

  @PostMapping
   ResponseEntity<AdoptionDocDTOResponse> create(@Validated @RequestBody AdoptionDocDTORequest request);

  @GetMapping
   ResponseEntity<List<AdoptionDocDTOResponse>> findAll();

  @GetMapping("/{id}")
   ResponseEntity<AdoptionDocDTOResponse> findById(@PathVariable String id);

  @DeleteMapping("/{id}")
   ResponseEntity<Void> delete(@PathVariable String id);
}

package br.com.compass.adoptionapi.exceptions;

public class PetAlreadyAdoptedException extends RuntimeException {
  public PetAlreadyAdoptedException(String message) {
    super(message);
  }
}

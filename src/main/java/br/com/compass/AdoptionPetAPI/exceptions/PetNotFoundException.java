package br.com.compass.AdoptionPetAPI.exceptions;

public class PetNotFoundException extends RuntimeException{
  public PetNotFoundException(String message){
    super(message);
  }
}

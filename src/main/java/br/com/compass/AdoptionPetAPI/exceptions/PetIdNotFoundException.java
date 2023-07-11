package br.com.compass.AdoptionPetAPI.exceptions;

public class PetIdNotFoundException extends RuntimeException{
  public PetIdNotFoundException(String message){
    super(message);
  }
}

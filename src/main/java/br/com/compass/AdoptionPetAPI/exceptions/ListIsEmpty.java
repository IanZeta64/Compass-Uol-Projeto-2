package br.com.compass.AdoptionPetAPI.exceptions;

public class ListIsEmpty extends RuntimeException {
  public ListIsEmpty(String message){
    super(message);
  }
}

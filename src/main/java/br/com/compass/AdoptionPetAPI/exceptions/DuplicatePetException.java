package br.com.compass.AdoptionPetAPI.exceptions;

public class DuplicatePetException extends RuntimeException {
    public DuplicatePetException(String message) {
        super(message);
    }
}
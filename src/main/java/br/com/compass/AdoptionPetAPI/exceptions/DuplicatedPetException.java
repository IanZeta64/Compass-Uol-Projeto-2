package br.com.compass.AdoptionPetAPI.exceptions;

public class DuplicatedPetException extends RuntimeException {
    public DuplicatedPetException(String message) {
        super(message);
    }
}

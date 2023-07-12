package br.com.compass.petapi.exceptions;

public class DuplicatedPetException extends RuntimeException {
    public DuplicatedPetException(String message) {
        super(message);
    }
}

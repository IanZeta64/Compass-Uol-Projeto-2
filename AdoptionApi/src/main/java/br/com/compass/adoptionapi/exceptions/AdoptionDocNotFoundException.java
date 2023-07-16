package br.com.compass.adoptionapi.exceptions;

public class AdoptionDocNotFoundException extends RuntimeException {
    public AdoptionDocNotFoundException(String message) {
        super(message);
    }
}
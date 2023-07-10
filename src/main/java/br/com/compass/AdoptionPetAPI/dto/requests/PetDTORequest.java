package br.com.compass.AdoptionPetAPI.dto.requests;
import br.com.compass.AdoptionPetAPI.enums.Gender;
import br.com.compass.AdoptionPetAPI.enums.Specie;
import java.time.LocalDate;

public record PetDTORequest(String name, Gender gender, Specie specie, LocalDate birthDate) {
}

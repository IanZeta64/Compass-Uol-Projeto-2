package br.com.compass.petapi.dto.requests;
import br.com.compass.petapi.enums.Gender;
import br.com.compass.petapi.enums.Specie;
import java.time.LocalDate;

public record PetDTORequest(String name, Gender gender, Specie specie, LocalDate birthDate) {
}

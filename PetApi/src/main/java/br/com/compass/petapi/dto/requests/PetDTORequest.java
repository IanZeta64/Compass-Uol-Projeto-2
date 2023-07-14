package br.com.compass.petapi.dto.requests;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record PetDTORequest(

        @NotBlank(message = "Pet's name can not be blank")
        @Pattern(regexp = "^[a-zA-Z]+$", message = "The name should only contain letters")
        @Size(max = 25, message = "The name's pet should have a maximum of 25 characters")
        @Size(min = 2, message = "The name's pet should have a minimum of 2 characters")
        String name,

        @NotBlank(message = "Pet's gender can not be blank")
        @Pattern(regexp = "^(MALE|FEMALE)+$", message = "The gender should be 'MALE' or 'FEMALE'")
        String gender,

        @NotBlank(message = "Pet's specie can not be blank")
        @Pattern(regexp = "^(DOG|CAT|OTHER)+$", message = "The specie should be 'DOG' or 'CAT' or 'OTHER'")
        String specie,

        @NotNull(message = "Pet's birth date can not be null")
        @PastOrPresent(message = "The birth date should be in the past or present")
        LocalDate birthDate)
{
}

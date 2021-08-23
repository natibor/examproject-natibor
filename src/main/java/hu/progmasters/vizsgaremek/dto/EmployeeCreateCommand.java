package hu.progmasters.vizsgaremek.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeCreateCommand {

    @NotBlank(message = "Can't be blank!")
    private String name;

}

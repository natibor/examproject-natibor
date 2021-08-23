package hu.progmasters.vizsgaremek.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class EmployeeCreateCommand {

    @NotBlank(message = "Can't be blank!")
    private String name;

    private boolean active = true;

}

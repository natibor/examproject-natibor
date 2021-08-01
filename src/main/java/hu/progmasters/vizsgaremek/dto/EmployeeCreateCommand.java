package hu.progmasters.vizsgaremek.dto;


import hu.progmasters.vizsgaremek.domain.Project;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class EmployeeCreateCommand {

    @NotBlank(message = "Can't be blank!")
    private String name;

    @NotBlank(message = "Can't be blank!")
    private Project project;
}

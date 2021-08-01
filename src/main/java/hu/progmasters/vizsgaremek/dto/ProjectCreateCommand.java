package hu.progmasters.vizsgaremek.dto;

import hu.progmasters.vizsgaremek.domain.Employee;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class ProjectCreateCommand {

    @NotBlank(message = "Can't be blank!")
    private String name;

    @NotBlank(message = "Can't be blank!")
    private Employee leader;

    @NotBlank(message = "Can't be blank!")
    private List<Employee> members;
}

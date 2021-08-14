package hu.progmasters.vizsgaremek.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class ProjectCreateCommand {

    @NotBlank(message = "Can't be blank!")
    private String name;

    @NotBlank(message = "Can't be blank!")
    private String description;

}

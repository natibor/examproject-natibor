package hu.progmasters.vizsgaremek.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProjectInfo {

    private int id;
    private String name;
    private String description;
    private EmployeeInfo leader;
    private boolean active;
}

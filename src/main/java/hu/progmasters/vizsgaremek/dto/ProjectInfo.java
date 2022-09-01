package hu.progmasters.vizsgaremek.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProjectInfo {

    private Integer id;
    private String name;
    private String description;
    private boolean inProgress;
    private double totalWorkHours;
}

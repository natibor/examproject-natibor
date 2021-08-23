package hu.progmasters.vizsgaremek.dto;

import hu.progmasters.vizsgaremek.domain.Project;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class EmployeeInfo {

    private int id;
    private String name;
    private double bookedHours;
    private int workedDays;
    private boolean active;
    private List<Project> projects;
}

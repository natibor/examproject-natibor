package hu.progmasters.vizsgaremek.dto;

import hu.progmasters.vizsgaremek.domain.JobTime;
import hu.progmasters.vizsgaremek.domain.Project;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmployeeInfo {

    private int id;
    private String name;
    private JobTime jobTime;
    private int workTime;
    private Project project;

}

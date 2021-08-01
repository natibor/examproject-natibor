package hu.progmasters.vizsgaremek.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmployeeInfo {

    private int id;
    private String name;
    private ProjectInfo projectInfo;
    private double workTime;

}

package hu.progmasters.vizsgaremek.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ProjectInfo {

    private int id;
    private String name;
    private EmployeeInfo leader;
    private List<EmployeeInfo> members;
    private double totalWorkTime;

}

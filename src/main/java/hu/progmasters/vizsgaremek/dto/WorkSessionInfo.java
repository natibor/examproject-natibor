package hu.progmasters.vizsgaremek.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WorkSessionInfo {

    private int id;
    private int employeeId;
    private  int projectId;
    private double bookedHours;

}

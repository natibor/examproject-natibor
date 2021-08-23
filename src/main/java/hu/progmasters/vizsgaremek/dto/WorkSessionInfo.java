package hu.progmasters.vizsgaremek.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WorkSessionInfo {

    private Long id;
    private String employeeName;
    private String projectName;
    private double bookedHours;

}

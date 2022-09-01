package hu.progmasters.vizsgaremek.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
public class WorkSessionCreateCommand {

    @Min(value = 1, message = "Can't be zero!")
    private int employeeId;

    @Min(value = 1, message = "Can't be zero!")
    private int projectId;

    @Min(value = 1, message = "Booking less than 1 hour is not allowed!")
    @Max(value = 12, message = "Booking more than 12 hours is not allowed!")
    private double bookedHours;

}

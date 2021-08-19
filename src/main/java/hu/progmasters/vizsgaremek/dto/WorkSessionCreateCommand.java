package hu.progmasters.vizsgaremek.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class WorkSessionCreateCommand {

    @NotNull(message = "Can't be null!")
    private int employeeId;

    @NotNull(message = "Can't be null!")
    private int projectId;

    @NotNull(message = "Can't be null!")
    @Min(value = 1, message = "Booking less than 1 hour is not allowed!")
    @Max(value = 12, message = "Booking more than 12 hours is not allowed!")
    private double bookedHours;

}

package hu.progmasters.vizsgaremek.domain;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Project {

    private int id;
    private String name;
    private Employee leader;
    private List<Employee> members;
    private double totalWorkTime;

}

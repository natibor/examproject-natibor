package hu.progmasters.vizsgaremek.domain;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToOne
    private Employee leader;

    @OneToMany(mappedBy = "project")
    private List<Employee> members;

    private int totalWorkTime;

}

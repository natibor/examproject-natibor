package hu.progmasters.vizsgaremek.service;

import hu.progmasters.vizsgaremek.domain.Employee;
import hu.progmasters.vizsgaremek.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class EmployeeServiceTest {

    @Autowired
    EmployeeRepository employeeRepository;

//    WorkSessionRepository workSessionRepository;
//    ProjectRepository projectRepository;
//    ModelMapper modelMapper = new ModelMapper();
//    EmployeeService employeeService= new EmployeeService(employeeRepository, workSessionRepository, projectRepository, modelMapper);

    @Test
    void testSaveEmployee() {
        Employee toSave = new Employee();
        toSave.setName("Neil Armstrong");

        Employee expectedNull = employeeRepository.findById(1).get();
        assertNull(expectedNull);

        Employee saved = employeeRepository.saveEmployee(toSave);
        assertEquals(1, saved.getId());

//                (employeeRepository.saveEmployee(toSave).getName()).isEqualTo("Neil Armstrong");
    }

}

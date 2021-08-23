package hu.progmasters.vizsgaremek.repository;

import hu.progmasters.vizsgaremek.domain.Employee;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeRepositoryTest {

    @Mock
    EntityManager entityManager;

    @InjectMocks
    EmployeeRepository employeeRepository;

    @Test
    void saveEmployee() {
        Employee toSave = new Employee();
//        when(entityManager.persist(toSave)).thenReturn(toSave);
//        toSave.setName("John Doe");
        Employee employee = employeeRepository.saveEmployee(toSave);

        assertThat(employee).isEqualTo(toSave);
    }

//    @Test
//    void findById() {
//        employeeRepository.saveEmployee(new Employee(1, "John Doe", 8.0, 1, true, null));
//
//        Employee employee = employeeRepository.findById(1).get();
//
//        assertThat(employee.getName()).isEqualTo("John Doe");
//    }

    @Test
    void updateEmployee() {
    }

    @Test
    void findAll() {
    }
}
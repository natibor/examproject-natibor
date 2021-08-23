package hu.progmasters.vizsgaremek.service;


import hu.progmasters.vizsgaremek.domain.Employee;
import hu.progmasters.vizsgaremek.dto.EmployeeCreateCommand;
import hu.progmasters.vizsgaremek.exception.EmployeeNotFoundException;
import hu.progmasters.vizsgaremek.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class WorkTimeServiceTest {

    @MockBean
    EmployeeRepository employeeRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    WorkTimeService workTimeService;

    private Employee johnDoe;
    private Employee johnDoeInactive;

    @BeforeEach
    void init() {

        johnDoe = new Employee();
        johnDoe.setId(0);
        johnDoe.setName("John Doe");
        johnDoe.setActive(true);

        johnDoeInactive = johnDoe;
        johnDoeInactive.setActive(false);
    }


    @Test
    void testSaveEmployeeSuccessful() {
        when(employeeRepository.saveEmployee(johnDoe)).thenReturn(johnDoe);
        when(employeeRepository.findAll()).thenReturn(List.of(johnDoe));
        EmployeeCreateCommand command = new EmployeeCreateCommand("John Doe");
        Employee saved = modelMapper.map(workTimeService.saveEmployee(command), Employee.class);
        assertEquals(johnDoe, saved);

    }

    @Test
    void testFindEmployeeById_NotFound() {
        when(employeeRepository.findById(13)).thenReturn(Optional.empty());
        assertThrows(EmployeeNotFoundException.class, () -> workTimeService.findEmployee(13));

    }

    @Test
    void testFindEmployeeById_Successful() {
        when(employeeRepository.findById(1)).thenReturn(Optional.of(johnDoe));
        assertEquals(johnDoe, modelMapper.map(workTimeService.findEmployee(1), Employee.class));
    }


    @Test
    void testListEmployees() {
        when(employeeRepository.findAll()).thenReturn(List.of());
        assertThat(workTimeService.listEmployees()).isEmpty();
    }

    @Test
    void testDeleteEmployee_Successful() {
        when(workTimeService.findEmployee(1)).thenReturn(johnDoe);
        when(workTimeService.deleteEmployee(1)).thenReturn(employeeRepository.updateEmployee(johnDoe));
        when(employeeRepository.updateEmployee(johnDoe)).thenReturn(johnDoeInactive);
        assertEquals(johnDoeInactive, workTimeService.deleteEmployee(1));
    }



}

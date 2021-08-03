package hu.progmasters.vizsgaremek.service;

import hu.progmasters.vizsgaremek.domain.Employee;
import hu.progmasters.vizsgaremek.dto.EmployeeCreateCommand;
import hu.progmasters.vizsgaremek.dto.EmployeeInfo;
import hu.progmasters.vizsgaremek.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    public EmployeeInfo saveEmployee(EmployeeCreateCommand command) {
        Employee employee = modelMapper.map(command, Employee.class);
        return modelMapper.map(employeeRepository.saveEmployee(employee), EmployeeInfo.class);
    }

    public List<EmployeeInfo> listEmployees() {
        return employeeRepository.findAll().stream()
                .map(employee -> modelMapper.map(employee, EmployeeInfo.class))
                .collect(Collectors.toList());
    }
}

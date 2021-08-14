package hu.progmasters.vizsgaremek.service;

import hu.progmasters.vizsgaremek.domain.Employee;
import hu.progmasters.vizsgaremek.domain.WorkSession;
import hu.progmasters.vizsgaremek.dto.EmployeeCreateCommand;
import hu.progmasters.vizsgaremek.dto.EmployeeInfo;
import hu.progmasters.vizsgaremek.exception.EmployeeNotFoundException;
import hu.progmasters.vizsgaremek.repository.EmployeeRepository;
import hu.progmasters.vizsgaremek.repository.ProjectRepository;
import hu.progmasters.vizsgaremek.repository.WorkSessionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final WorkSessionRepository workSessionRepository;
    private final ProjectRepository projectRepository;
    private final ModelMapper modelMapper;

    public EmployeeService(EmployeeRepository employeeRepository, WorkSessionRepository workSessionRepository, ProjectRepository projectRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.workSessionRepository = workSessionRepository;
        this.projectRepository = projectRepository;
        this.modelMapper = modelMapper;
    }

    public EmployeeInfo saveEmployee(EmployeeCreateCommand command) {
        Employee employee = modelMapper.map(command, Employee.class);
        return modelMapper.map(employeeRepository.saveEmployee(employee), EmployeeInfo.class);
    }

    public EmployeeInfo findEmployee(Integer id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isEmpty()) {
            throw new EmployeeNotFoundException();
        }
        List<WorkSession> workSessions = workSessionRepository.findByEmployeeId(id);
        double bookedHours = 0;
        List<String> projects = new ArrayList<>();
        for (WorkSession worksession : workSessions) {
            bookedHours += worksession.getBookedHours();
            String project = projectRepository.findById(worksession.getProjectId()).get().getName();
            if (projects.contains(project)) {
            } else {
                projects.add(project);
            }
        }
        EmployeeInfo employeeInfo = modelMapper.map(employee.get(), EmployeeInfo.class);
        employeeInfo.setBookedHours(bookedHours);
        employeeInfo.setWorkedDays(workSessions.size());
        employeeInfo.setProjects(projects);
        return employeeInfo;
    }

    public List<EmployeeInfo> listEmployees() {
        return employeeRepository.findAll().stream()
                .map(employee -> modelMapper.map(employee, EmployeeInfo.class))
                .collect(Collectors.toList());
    }

    public void deleteEmployee(int id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isEmpty()) {
            throw new EmployeeNotFoundException();
        }
        employee.get().setActive(false);
        employeeRepository.updateEmployee(employee.get());
    }
}

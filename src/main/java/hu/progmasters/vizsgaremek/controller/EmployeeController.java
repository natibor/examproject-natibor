package hu.progmasters.vizsgaremek.controller;

import hu.progmasters.vizsgaremek.dto.EmployeeCreateCommand;
import hu.progmasters.vizsgaremek.dto.EmployeeInfo;
import hu.progmasters.vizsgaremek.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeInfo saveEmployee(@Valid @RequestBody EmployeeCreateCommand command) {
        LOGGER.info(String.format("POST - Save new employee: %s", command));
        return employeeService.saveEmployee(command);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeInfo> listEmployees() {
        LOGGER.info("GET - List all employees");
        return employeeService.listEmployees();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeInfo findEmployee(@PathVariable int id) {
        LOGGER.info(String.format("GET - Find employee with id: %s", id));
        return employeeService.findEmployee(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteEmployee(@PathVariable int id) {
        LOGGER.info(String.format("DELETE - Delete employee with id: %s", id));
        employeeService.deleteEmployee(id);
    }

}

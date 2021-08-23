package hu.progmasters.vizsgaremek.controller;

import hu.progmasters.vizsgaremek.dto.EmployeeCreateCommand;
import hu.progmasters.vizsgaremek.dto.EmployeeInfo;
import hu.progmasters.vizsgaremek.service.WorkTimeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
@Tag(name = "Operations on Employees")
public class EmployeeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    private final WorkTimeService service;

    public EmployeeController(WorkTimeService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Save new employee")
    public EmployeeInfo saveEmployee(
            @Parameter(description = "Name of the new employee")
            @Valid @RequestBody EmployeeCreateCommand command) {
        LOGGER.info(String.format("POST - Save new employee: %s", command));
        return service.saveEmployee(command);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "List all active employees")
    public List<EmployeeInfo> listEmployees() {
        LOGGER.info("GET - List all active employees");
        return service.listEmployees();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Find an employee by ID")
    public EmployeeInfo findEmployee(
            @Parameter(description = "ID of the employee")
            @PathVariable int id) {
        LOGGER.info(String.format("GET - Find employee with id: %s", id));
        return service.showEmployeeDetails(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Delete an employee by ID",
            description = "This operation sets the employee inactive, but don't removes him/her from the database.")
    public void deleteEmployee(
            @Parameter(description = "ID of the employee")
            @PathVariable int id) {
        LOGGER.info(String.format("DELETE - Delete employee with id: %s", id));
        service.deleteEmployee(id);
    }

}

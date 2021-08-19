package hu.progmasters.vizsgaremek.controller;

import hu.progmasters.vizsgaremek.dto.WorkSessionCreateCommand;
import hu.progmasters.vizsgaremek.dto.WorkSessionInfo;
import hu.progmasters.vizsgaremek.service.WorkSessionService;
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
@RequestMapping("/api/worksession")
@Tag(name = "Operations on Work Sessions")
public class WorkSessionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkSessionController.class);

    private final WorkSessionService workSessionService;

    public WorkSessionController(WorkSessionService workSessionService) {
        this.workSessionService = workSessionService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Save new work session")
    public WorkSessionInfo saveWorkSession(
            @Parameter(description = "Employee ID, project ID and booked hours")
            @Valid @RequestBody WorkSessionCreateCommand command) {
        LOGGER.info(String.format("POST - Save new work session: %s", command));
        return workSessionService.saveWorkSession(command);
    }

    @GetMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Find the work sessions of an employee")
    public List<WorkSessionInfo> findByEmployeeId(
            @Parameter(description = "ID of the employee")
            @PathVariable int employeeId) {
        LOGGER.info(String.format("GET - Find work sessions for employee with id: %s", employeeId));
        return workSessionService.findByEmployeeId(employeeId);
    }

}

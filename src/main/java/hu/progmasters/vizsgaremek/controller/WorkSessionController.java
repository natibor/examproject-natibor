package hu.progmasters.vizsgaremek.controller;

import hu.progmasters.vizsgaremek.dto.WorkSessionCreateCommand;
import hu.progmasters.vizsgaremek.dto.WorkSessionInfo;
import hu.progmasters.vizsgaremek.service.WorkSessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/worksession")
public class WorkSessionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkSessionController.class);

    private final WorkSessionService workSessionService;

    public WorkSessionController(WorkSessionService workSessionService) {
        this.workSessionService = workSessionService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WorkSessionInfo saveWorkSession(@Valid @RequestBody WorkSessionCreateCommand command) {
        LOGGER.info(String.format("POST - Save new worksession: %s", command));
        return workSessionService.saveWorkSession(command);
    }

    @GetMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.OK)
    public List<WorkSessionInfo> findByEmployeeId(@PathVariable int employeeId) {
        LOGGER.info(String.format("GET - Find worksessions for employee with id: %s", employeeId));
        return workSessionService.findByEmployeeId(employeeId);
    }

}

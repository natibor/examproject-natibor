package hu.progmasters.vizsgaremek.controller;

import hu.progmasters.vizsgaremek.dto.WorkSessionCreateCommand;
import hu.progmasters.vizsgaremek.dto.WorkSessionInfo;
import hu.progmasters.vizsgaremek.dto.WorkSessionUpdateCommand;
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
@RequestMapping("/api/worksessions")
@Tag(name = "Operations on Work Sessions")
public class WorkSessionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkSessionController.class);

    private final WorkTimeService workTimeService;

    public WorkSessionController(WorkTimeService workTimeService) {
        this.workTimeService = workTimeService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Save new work session")
    public WorkSessionInfo save(
            @Parameter(description = "Employee ID, project ID and booked hours")
            @Valid @RequestBody WorkSessionCreateCommand command) {
        LOGGER.info(String.format("POST - Save a new work session: %s", command));
        return workTimeService.saveWorkSession(command);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "List all work sessions")
    public List<WorkSessionInfo> findAll() {
        LOGGER.info("GET - List all work sessions");
        return workTimeService.listWorkSessions();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Find a work session by ID")
    public WorkSessionInfo find(
            @Parameter(description = "ID of the work session")
            @PathVariable Long id) {
        LOGGER.info(String.format("GET - Find the work session with id: %s", id));
        return workTimeService.showWorkSessionDetails(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update an existing work session")
    public WorkSessionInfo update(
            @Parameter(description = "ID of the work session")
            @PathVariable Long id,
            @Valid @RequestBody WorkSessionUpdateCommand command) {
        LOGGER.info(String.format("PUT - Update the work session with id: %s", id));
        return workTimeService.updateWorkSession(id, command);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Delete a work session by ID",
            description = "This operation permanently deletes the project work session.")
    public void delete(
            @Parameter(name = "ID of the work session")
            @PathVariable Long id) {
        LOGGER.info(String.format("DELETE - Delete the work session with id: %s", id));
        workTimeService.deleteWorkSession(id);
    }

}

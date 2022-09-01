package hu.progmasters.vizsgaremek.controller;


import hu.progmasters.vizsgaremek.dto.ProjectCreateCommand;
import hu.progmasters.vizsgaremek.dto.ProjectInfo;
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
@RequestMapping("/api/projects")
@Tag(name = "Operations on Projects")
public class ProjectController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    private WorkTimeService workTimeService;

    public ProjectController(WorkTimeService workTimeService) {
        this.workTimeService = workTimeService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Save new project")
    public ProjectInfo save(
            @Parameter(description = "Name and description of the new project")
            @Valid @RequestBody ProjectCreateCommand command) {
        return workTimeService.saveProject(command);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "List all projects")
    public List<ProjectInfo> findAll() {
        LOGGER.info("GET - List all projects");
        return workTimeService.listProjects();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Find a project by ID")
    public ProjectInfo find(
            @Parameter(description = "ID of the project")
            @PathVariable int id) {
        LOGGER.info(String.format("GET - Find project with id: %s", id));
        return workTimeService.showProjectDetails(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Delete a project by ID",
            description = "This operation sets the project inactive, but don't removes it from the database.")
    public void delete(
            @Parameter(name = "ID of the project")
            @PathVariable int id) {
        LOGGER.info(String.format("DELETE - Delete project with id: %s", id));
        workTimeService.deleteProject(id);
    }
}

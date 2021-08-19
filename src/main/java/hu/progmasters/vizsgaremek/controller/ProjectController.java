package hu.progmasters.vizsgaremek.controller;


import hu.progmasters.vizsgaremek.dto.ProjectCreateCommand;
import hu.progmasters.vizsgaremek.dto.ProjectInfo;
import hu.progmasters.vizsgaremek.service.ProjectService;
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
@RequestMapping("/api/project")
@Tag(name = "Operations on Projects")
public class ProjectController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    private ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Save new project")
    public ProjectInfo save(
            @Parameter(description = "Name and description of the new project")
            @Valid @RequestBody ProjectCreateCommand command) {
        return projectService.saveProject(command);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "List all active projects")
    public List<ProjectInfo> findAll() {
        return projectService.listProjects();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Find a project by ID")
    public ProjectInfo findProject(
            @Parameter(description = "ID of the project")
            @PathVariable int id) {
        LOGGER.info(String.format("GET - Find project with id: %s", id));
        return projectService.findProject(id);
    }

    @PutMapping("/{projectId}/leader/{employeeId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Set the leader of a project")
    public ProjectInfo setProjectLeader(
            @Parameter(name = "ID of the project")
            @PathVariable int projectId,
            @Parameter(name = "ID of the employee")
            @PathVariable int employeeId) {
        LOGGER.info(String.format("PUT - Set leader with employeeId %s for project %s", employeeId, projectId));
        return projectService.setProjectLeader(projectId, employeeId);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Delete a project by ID",
            description = "This operation sets the project inactive, but don't removes it from the database.")
    public void deleteEmployee(
            @Parameter(name = "ID of the project")
            @PathVariable int id) {
        LOGGER.info(String.format("DELETE - Delete project with id: %s", id));
        projectService.deleteProject(id);
    }


}

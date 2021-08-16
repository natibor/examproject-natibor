package hu.progmasters.vizsgaremek.controller;


import hu.progmasters.vizsgaremek.dto.ProjectCreateCommand;
import hu.progmasters.vizsgaremek.dto.ProjectInfo;
import hu.progmasters.vizsgaremek.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    private ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectInfo save(@Valid @RequestBody ProjectCreateCommand command) {
        return projectService.saveProject(command);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProjectInfo> findAll() {
        return projectService.listProjects();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProjectInfo findProject(@PathVariable int id) {
        LOGGER.info(String.format("GET - Find project with id: %s", id));
        return projectService.findProject(id);
    }

    @PutMapping("/{projectId}/leader/{employeeId}")
    @ResponseStatus(HttpStatus.OK)
    public ProjectInfo setProjectLeader(@PathVariable int projectId, @PathVariable int employeeId) {
        LOGGER.info(String.format("PUT - Set leader with employeeId %s for project %s", employeeId, projectId));
        return projectService.setProjectLeader(projectId, employeeId);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteEmployee(@PathVariable int id) {
        LOGGER.info(String.format("DELETE - Delete project with id: %s", id));
        projectService.deleteProject(id);
    }


}

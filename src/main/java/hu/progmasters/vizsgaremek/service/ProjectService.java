package hu.progmasters.vizsgaremek.service;

import hu.progmasters.vizsgaremek.domain.Employee;
import hu.progmasters.vizsgaremek.domain.Project;
import hu.progmasters.vizsgaremek.domain.WorkSession;
import hu.progmasters.vizsgaremek.dto.ProjectCreateCommand;
import hu.progmasters.vizsgaremek.dto.ProjectInfo;
import hu.progmasters.vizsgaremek.exception.EmployeeNotFoundException;
import hu.progmasters.vizsgaremek.exception.ProjectNotFoundException;
import hu.progmasters.vizsgaremek.repository.EmployeeRepository;
import hu.progmasters.vizsgaremek.repository.ProjectRepository;
import hu.progmasters.vizsgaremek.repository.WorkSessionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final EmployeeRepository employeeRepository;
    private final WorkSessionRepository workSessionRepository;
    private final ModelMapper modelMapper;

    public ProjectService(ProjectRepository projectRepository,
                          EmployeeRepository employeeRepository,
                          WorkSessionRepository workSessionRepository, ModelMapper modelMapper) {
        this.projectRepository = projectRepository;
        this.employeeRepository = employeeRepository;
        this.workSessionRepository = workSessionRepository;
        this.modelMapper = modelMapper;
    }

    public ProjectInfo saveProject(ProjectCreateCommand command) {
        Project toSave = modelMapper.map(command, Project.class);
        return modelMapper.map(projectRepository.saveProject(toSave), ProjectInfo.class);
    }

    public ProjectInfo findProject(Integer id) {
        Optional<Project> found = projectRepository.findById(id);
        if (found.isEmpty()) {
            throw new ProjectNotFoundException();
        }
        List<WorkSession> workSessions = workSessionRepository.findByProjectId(id);
        double totalWorkHours = 0;
        for (WorkSession worksession : workSessions) {
            totalWorkHours += worksession.getBookedHours();
        }
        ProjectInfo projectInfo = modelMapper.map(found.get(),ProjectInfo.class);
        projectInfo.setTotalWorkHours(totalWorkHours);
        return projectInfo;
    }

    public ProjectInfo setProjectLeader(Integer projectId, Integer employeeId) {
        Optional<Project> toUpdate = projectRepository.findById(projectId);
        if (toUpdate.isEmpty()) {
            throw new ProjectNotFoundException();
        }
        Optional<Employee> leaderToSet = employeeRepository.findById(employeeId);
        if (leaderToSet.isEmpty()) {
            throw new EmployeeNotFoundException();
        }
        toUpdate.get().setLeader(leaderToSet.get());
        employeeRepository.updateEmployee(leaderToSet.get());
        return modelMapper.map(projectRepository.updateProject(toUpdate.get()), ProjectInfo.class);
    }


    public List<ProjectInfo> listProjects() {
        List<Project> projects = projectRepository.findAll();
        List<ProjectInfo> projectInfos = new ArrayList<>();
        for (Project project : projects) {
            ProjectInfo info = modelMapper.map(project, ProjectInfo.class);
            projectInfos.add(info);
        }
        return projectInfos;
    }

    public void deleteProject(int id) {
        Optional<Project> project = projectRepository.findById(id);
        if (project.isEmpty()) {
            throw new ProjectNotFoundException();
        }
        project.get().setActive(false);
        projectRepository.updateProject(project.get());
    }

}

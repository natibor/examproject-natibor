package hu.progmasters.vizsgaremek.service;

import hu.progmasters.vizsgaremek.domain.Project;
import hu.progmasters.vizsgaremek.dto.ProjectCreateCommand;
import hu.progmasters.vizsgaremek.dto.ProjectInfo;
import hu.progmasters.vizsgaremek.repository.EmployeeRepository;
import hu.progmasters.vizsgaremek.repository.ProjectRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProjectService {

    private final ProjectRepository projectRepository;
//    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public ProjectService(ProjectRepository projectRepository,
//                          EmployeeRepository employeeRepository,
                          ModelMapper modelMapper) {
        this.projectRepository = projectRepository;
//        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    public ProjectInfo saveProject(ProjectCreateCommand command) {
        Project toSave = modelMapper.map(command, Project.class);
        return modelMapper.map(projectRepository.saveProject(toSave), ProjectInfo.class);
    }

//    public List<ProjectInfo> listProjects() {
//        List<Project> projects = projectRepository.findAll();
//        List<ProjectInfo> projectInfos = new ArrayList<>();
//        for (Project project : projects) {
//            ProjectInfo info = modelMapper.map(project, ProjectInfo.class);
//            List<EmployeeInfo> employeeInfos = project.getMembers().stream()
//                    .map(employee -> modelMapper.map(employee, EmployeeInfo.class))
//                    .collect(Collectors.toList());
//            info.setMembers(employeeInfos);
//            projectInfos.add(info);
//        }
//        return projectInfos;
//    }
}

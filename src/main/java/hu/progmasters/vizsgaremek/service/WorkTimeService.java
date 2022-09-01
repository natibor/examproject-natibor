package hu.progmasters.vizsgaremek.service;

import hu.progmasters.vizsgaremek.domain.Employee;
import hu.progmasters.vizsgaremek.domain.Project;
import hu.progmasters.vizsgaremek.domain.WorkSession;
import hu.progmasters.vizsgaremek.dto.*;
import hu.progmasters.vizsgaremek.exception.EmployeeNotFoundException;
import hu.progmasters.vizsgaremek.exception.ProjectNotFoundException;
import hu.progmasters.vizsgaremek.exception.WorkSessionNotFoundException;
import hu.progmasters.vizsgaremek.repository.EmployeeRepository;
import hu.progmasters.vizsgaremek.repository.ProjectRepository;
import hu.progmasters.vizsgaremek.repository.WorkSessionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class WorkTimeService {

    private final EmployeeRepository employeeRepository;
    private final WorkSessionRepository workSessionRepository;
    private final ProjectRepository projectRepository;
    private final ModelMapper modelMapper;

    public WorkTimeService(EmployeeRepository employeeRepository,
                           WorkSessionRepository workSessionRepository,
                           ProjectRepository projectRepository,
                           ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.workSessionRepository = workSessionRepository;
        this.projectRepository = projectRepository;
        this.modelMapper = modelMapper;
    }

// Employee methods

    public EmployeeInfo saveEmployee(EmployeeCreateCommand command) {
        Employee employee = modelMapper.map(command, Employee.class);
        return modelMapper.map(employeeRepository.save(employee), EmployeeInfo.class);
    }

    protected Employee findEmployee(Integer id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isEmpty()) {
            throw new EmployeeNotFoundException();
        }
        return employee.get();
    }

    public EmployeeInfo showEmployeeDetails(Integer id) {
        Employee toShow = findEmployee(id);
        List<WorkSession> workSessions = workSessionRepository.findByEmployeeId(id);
        double bookedHours = 0;
        List<Project> projects = new ArrayList<>();
        for (WorkSession worksession : workSessions) {
            bookedHours += worksession.getBookedHours();
            Project project = projectRepository.findById(worksession.getProjectId()).get();
            if (!projects.contains(project)) {
                projects.add(project);
            }
        }
        EmployeeInfo employeeInfo = modelMapper.map(toShow, EmployeeInfo.class);
        employeeInfo.setBookedHours(bookedHours);
        employeeInfo.setWorkedDays(workSessions.size());
        employeeInfo.setProjects(projects);
        return employeeInfo;
    }

    public List<EmployeeInfo> listEmployees() {
        return employeeRepository.findAll().stream()
                .map(employee -> showEmployeeDetails(employee.getId()))
                .collect(Collectors.toList());
    }

    public Employee deleteEmployee(int id) {
        Employee toDelete = findEmployee(id);
        toDelete.setActive(false);
        return employeeRepository.update(toDelete);
    }

// Project methods

    public ProjectInfo saveProject(ProjectCreateCommand command) {
        Project toSave = modelMapper.map(command, Project.class);
        return modelMapper.map(projectRepository.save(toSave), ProjectInfo.class);
    }

    public Project findProject(Integer id) {
        Optional<Project> project = projectRepository.findById(id);
        if (project.isEmpty()) {
            throw new ProjectNotFoundException();
        }
        return project.get();
    }

    public ProjectInfo showProjectDetails(Integer id) {
        Project toShow = findProject(id);
        List<WorkSession> workSessions = workSessionRepository.findByProjectId(id);
        double totalWorkHours = 0;
        for (WorkSession worksession : workSessions) {
            totalWorkHours += worksession.getBookedHours();
        }
        ProjectInfo projectInfo = modelMapper.map(toShow, ProjectInfo.class);
        projectInfo.setTotalWorkHours(totalWorkHours);
        return projectInfo;
    }

    public List<ProjectInfo> listProjects() {
        return projectRepository.findAll().stream()
                .map(project -> showProjectDetails(project.getId()))
                .collect(Collectors.toList());
    }

    public void deleteProject(int id) {
        Project toDelete = findProject(id);
        toDelete.setInProgress(false);
        projectRepository.update(toDelete);
    }

// WorkSession methods

    public WorkSessionInfo saveWorkSession(WorkSessionCreateCommand command) {
        WorkSession toSave = modelMapper.map(command, WorkSession.class);
        workSessionRepository.save(toSave);
        Employee toUpdate = findEmployee(command.getEmployeeId());
        Project toAdd = findProject(command.getProjectId());
        List<Project> projects = new ArrayList<>();
        if (toUpdate.getProjects() != null) {
            if (!toUpdate.getProjects().contains(toAdd)) {
                projects.add(toAdd);
                toUpdate.setProjects(projects);
                employeeRepository.update(toUpdate);
            }
        } else {
            projects.add(toAdd);
            toUpdate.setProjects(projects);
        }
        return showWorkSessionDetails(toSave.getId());
    }

    public WorkSession findWorkSession(Long id) {
        Optional<WorkSession> workSession = workSessionRepository.findById(id);
        if (workSession.isEmpty()) {
            throw new WorkSessionNotFoundException();
        }
        return workSession.get();
    }

    public WorkSessionInfo showWorkSessionDetails(Long id) {
        WorkSession toShow = findWorkSession(id);
        WorkSessionInfo workSessionInfo = new WorkSessionInfo();
        workSessionInfo.setId(toShow.getId());
        workSessionInfo.setEmployeeName(findEmployee(toShow.getEmployeeId()).getName());
        workSessionInfo.setProjectName(findProject(toShow.getProjectId()).getName());
        workSessionInfo.setBookedHours(toShow.getBookedHours());
        return workSessionInfo;
    }

    public List<WorkSessionInfo> listWorkSessions() {
        List<WorkSessionInfo> workSessionInfos = new ArrayList<>();
        List<WorkSession> workSessions = workSessionRepository.findAll();
        for (WorkSession workSession : workSessions) {
            WorkSessionInfo toAdd = showWorkSessionDetails(workSession.getId());
            workSessionInfos.add(toAdd);
        }
        return workSessionInfos;
    }

    public WorkSessionInfo updateWorkSession(Long id, WorkSessionUpdateCommand command) {
        WorkSession toUpdate = findWorkSession(id);
        toUpdate.setProjectId(command.getProjectId());
        toUpdate.setBookedHours(command.getBookedHours());
        workSessionRepository.update(toUpdate);
        return showWorkSessionDetails(id);
    }

    public void deleteWorkSession(Long id) {
        workSessionRepository.delete(findWorkSession(id));
    }
}

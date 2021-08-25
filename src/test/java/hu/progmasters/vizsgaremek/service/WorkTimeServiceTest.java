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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class WorkTimeServiceTest {

    EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
    ProjectRepository projectRepository = mock(ProjectRepository.class);
    WorkSessionRepository workSessionRepository = mock(WorkSessionRepository.class);

    ModelMapper modelMapper = new ModelMapper();

    WorkTimeService workTimeService = new WorkTimeService(
            employeeRepository,
            workSessionRepository,
            projectRepository,
            modelMapper);

    private EmployeeCreateCommand employeeCreateCommand;
    private Employee employee;
    private EmployeeInfo employeeInfo;
    private Employee employeeInDatabase;
    private Employee employeeDeleted;
    private Employee employeeUpdated;

    private ProjectCreateCommand projectCreateCommand;
    private Project project;
    private ProjectInfo projectInfo;
    private Project projectInDatabase;
    private Project projectDeleted;


    private WorkSessionCreateCommand workSessionCreateCommand;
    private WorkSession workSession;
    private WorkSessionInfo workSessionInfo;
    private WorkSessionUpdateCommand workSessionUpdateCommand;
    private WorkSession workSessionInDatabase;
    private WorkSession workSessionUpdated;

    @BeforeEach
    void initAll() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        createTestData();
    }

    void createTestData() {

        employeeCreateCommand = new EmployeeCreateCommand();
        employeeCreateCommand.setName("Elon Musk");

        employee = modelMapper.map(employeeCreateCommand, Employee.class);
//        employee.setId(1);

        employeeInfo = modelMapper.map(employeeCreateCommand, EmployeeInfo.class);
        employeeInfo.setId(1);

        employeeInDatabase = modelMapper.map(employeeCreateCommand, Employee.class);
        employeeInDatabase.setId(1);

        employeeDeleted = modelMapper.map(employeeCreateCommand, Employee.class);
        employeeDeleted.setId(1);
        employeeDeleted.setActive(false);

        employeeUpdated = modelMapper.map(employeeCreateCommand, Employee.class);

        projectCreateCommand = new ProjectCreateCommand();
        projectCreateCommand.setName("Starship");
        projectCreateCommand.setDescription(
                "I thought it would be funny to make it more pointy, so we did.");

        project = modelMapper.map(projectCreateCommand, Project.class);
//        project.setId(1);

        projectInfo = modelMapper.map(projectCreateCommand, ProjectInfo.class);
        projectInfo.setId(1);

        projectInDatabase = modelMapper.map(projectCreateCommand, Project.class);
        projectInDatabase.setId(1);

        projectDeleted = modelMapper.map(projectCreateCommand, Project.class);
        projectDeleted.setId(1);
        projectDeleted.setInProgress(false);


        workSessionCreateCommand = new WorkSessionCreateCommand();
        workSessionCreateCommand.setEmployeeId(1);
        workSessionCreateCommand.setProjectId(1);
        workSessionCreateCommand.setBookedHours(9.5);

        workSession = modelMapper.map(workSessionCreateCommand, WorkSession.class);

        workSessionInfo = modelMapper.map(workSessionCreateCommand, WorkSessionInfo.class);
        workSessionInfo.setId(1L);

        workSessionInDatabase = modelMapper.map(workSessionCreateCommand, WorkSession.class);
        workSessionInDatabase.setId(1L);

        workSessionUpdateCommand = new WorkSessionUpdateCommand(1, 7.5);
    }


    @Test
    void testListEmployees_emptyDatabase() {
        when(employeeRepository.findAll()).thenReturn(List.of());
        List<EmployeeInfo> employees = workTimeService.listEmployees();

        assertEquals(List.of(), employees);
        assertThat(workTimeService.listEmployees())
                .hasSize(0);

        verify(employeeRepository, times(2)).findAll();
        verifyNoMoreInteractions(employeeRepository);
    }

    @Test
    void testListProjects_emptyDatabase() {
        when(projectRepository.findAll()).thenReturn(List.of());
        List<ProjectInfo> projects = workTimeService.listProjects();

        assertEquals(List.of(), projects);
        assertThat(workTimeService.listProjects())
                .hasSize(0);

        verify(projectRepository, times(2)).findAll();
        verifyNoMoreInteractions(projectRepository);
    }

    @Test
    void testListWorkSessions_emptyDatabase() {
        when(workSessionRepository.findAll()).thenReturn(List.of());
        List<WorkSessionInfo> workSessions = workTimeService.listWorkSessions();

        assertEquals(List.of(), workSessions);
        assertThat(workTimeService.listWorkSessions())
                .hasSize(0);

        verify(workSessionRepository, times(2)).findAll();
        verifyNoMoreInteractions(workSessionRepository);
    }

    @Test
    void testSaveEmployee_Successful() {
        when(employeeRepository.save(employee)).thenReturn(employeeInDatabase);
        EmployeeInfo employeeSaved = workTimeService.saveEmployee(employeeCreateCommand);
        assertEquals(employeeInfo, employeeSaved);

        verify(employeeRepository, times(1)).save(any());
        verifyNoMoreInteractions(employeeRepository);
    }

    @Test
    void testSaveProject_Successful() {
        when(projectRepository.save(project)).thenReturn(projectInDatabase);
        ProjectInfo projectSaved = workTimeService.saveProject(projectCreateCommand);
        assertEquals(projectInfo, projectSaved);

        verify(projectRepository, times(1)).save(any());
        verifyNoMoreInteractions(projectRepository);
    }

/* TODO
    @Test
    void testSaveWorkSession_Successful() {
        employee.setId(1);
        employeeUpdated.setProjects(List.of(project));

        when(workSessionRepository.save(workSession)).thenReturn(workSessionInDatabase);
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        when(projectRepository.findById(1)).thenReturn(Optional.of(project));
        when(workSessionRepository.findById(1L)).thenReturn(Optional.of(workSession));
        when(employeeRepository.update(employee)).thenReturn(employeeUpdated);

        WorkSessionInfo workSessionSaved = workTimeService.saveWorkSession(workSessionCreateCommand);

        assertEquals(workSessionInfo, workSessionSaved);

        verify(workSessionRepository, times(1)).save(any());
        verifyNoMoreInteractions(workSessionRepository);
    }
*/

    @Test
    void testFindEmployee_ThrowsException() {
        when(employeeRepository.findById(13)).thenReturn(Optional.empty());
        assertThrows(EmployeeNotFoundException.class, () -> workTimeService.findEmployee(13));
        verify(employeeRepository, times(1)).findById(13);
        verifyNoMoreInteractions(employeeRepository);
    }

    @Test
    void testFindEmployee_Successful() {
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        assertThat(workTimeService.findEmployee(1))
                .isEqualTo(employee);
        verify(employeeRepository, times(1)).findById(1);
        verifyNoMoreInteractions(employeeRepository);
    }


    @Test
    void testShowEmployeeDetails() {
        employeeInfo.setBookedHours(9.5);
        employeeInfo.setWorkedDays(1);
        employeeInfo.setProjects(List.of(project));
        employee.setId(1);
        project.setId(1);
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        when(workSessionRepository.findByEmployeeId(1)).thenReturn(List.of(workSession));
        when(projectRepository.findById(1)).thenReturn(Optional.of(project));
        assertThat(workTimeService.showEmployeeDetails(1))
                .isEqualTo(employeeInfo);

        verify(employeeRepository, times(1)).findById(1);
        verify(workSessionRepository, times(1)).findByEmployeeId(1);
        verify(projectRepository, times(1)).findById(1);
        verifyNoMoreInteractions(workSessionRepository, employeeRepository, projectRepository);

    }

    @Test
    void testShowProjectDetails() {
        projectInfo.setTotalWorkHours(9.5);
        project.setId(1);
        when(projectRepository.findById(1)).thenReturn(Optional.of(project));
        when(workSessionRepository.findByProjectId(1)).thenReturn(List.of(workSession));
        assertThat(workTimeService.showProjectDetails(1))
                .isEqualTo(projectInfo);

        verify(projectRepository, times(1)).findById(1);
        verify(workSessionRepository, times(1)).findByProjectId(1);
        verifyNoMoreInteractions(projectRepository, workSessionRepository);
    }

    @Test
    void testShowWorkSessionDetails() {
        workSession.setId(1L);
        workSessionInfo.setEmployeeName("Elon Musk");
        workSessionInfo.setProjectName("Starship");
        when(workSessionRepository.findById(1L)).thenReturn(Optional.of(workSession));
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        when(projectRepository.findById(1)).thenReturn(Optional.of(project));
        assertThat(workTimeService.showWorkSessionDetails(1L))
                .isEqualTo(workSessionInfo);

        verify(workSessionRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).findById(1);
        verify(projectRepository, times(1)).findById(1);
        verifyNoMoreInteractions(workSessionRepository, employeeRepository, projectRepository);
    }


    @Test
    void testListEmployees_EmptyList() {
        when(employeeRepository.findAll()).thenReturn(List.of());
        assertThat(workTimeService.listEmployees()).isEmpty();
        verify(employeeRepository, times(1)).findAll();
        verifyNoMoreInteractions(employeeRepository);
    }


    @Test
    void testDeleteEmployee_throwsException() {
        when(employeeRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(EmployeeNotFoundException.class, () -> workTimeService.findEmployee(1));
        verify(employeeRepository, times(1)).findById(1);
        verifyNoMoreInteractions(employeeRepository);
    }


    @Test
    void testDeleteEmployee_Successful() {
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employeeDeleted));
        workTimeService.deleteEmployee(1);
        Employee employeeToDelete = workTimeService.findEmployee(1);
        assertEquals(employeeToDelete, employeeDeleted);
        verify(employeeRepository, times(2)).findById(1);
        verify(employeeRepository, times(1)).update(employeeToDelete);
        verifyNoMoreInteractions(employeeRepository);
    }

    @Test
    void testDeleteProject_throwsException() {
        when(projectRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(ProjectNotFoundException.class, () -> workTimeService.findProject(1));
        verify(projectRepository, times(1)).findById(1);
        verifyNoMoreInteractions(projectRepository);
    }


    @Test
    void testDeleteProject_Successful() {
        when(projectRepository.findById(1)).thenReturn(Optional.of(projectDeleted));
        workTimeService.deleteProject(1);
        Project projectToDelete = workTimeService.findProject(1);
        assertEquals(projectToDelete, projectDeleted);
        verify(projectRepository, times(2)).findById(1);
        verify(projectRepository, times(1)).update(projectToDelete);
        verifyNoMoreInteractions(projectRepository);
    }
/* TODO
    @Test
    void testUpdateWorkSession_Succesful() {
        when(workSessionRepository.update(workSession)).thenReturn(workSessionUpdated);
//        when(workTimeService.showWorkSessionDetails(1L)).thenReturn(workSessionUpdated);

        workTimeService.updateWorkSession(1L, workSessionUpdateCommand);
        WorkSession workSessionToUpdate = workTimeService.findWorkSession(1L);
        assertEquals(workSessionToUpdate, workSessionUpdated);
        verify(workSessionRepository, times(2)).findById(1L);
        verify(workSessionRepository, times(1)).update(workSessionToUpdate);
        verifyNoMoreInteractions(workSessionRepository);
    }
*/

    @Test
    void testDeleteWorkSession_throwsException() {
        when(workSessionRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(WorkSessionNotFoundException.class, () -> workTimeService.findWorkSession(1L));
        verify(workSessionRepository, times(1)).findById(1L);
        verifyNoMoreInteractions(workSessionRepository);
    }

    @Test
    public void testDeleteWorkSession_isCalled() {
        workSession.setId(1L);
        when(workSessionRepository.findById(1L)).thenReturn(Optional.of(workSession));
        doNothing().when(workSessionRepository).delete(workSession);
        workTimeService.deleteWorkSession(1L);
        verify(workSessionRepository, times(1)).delete(workSession);
    }


}

package hu.progmasters.vizsgaremek.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.progmasters.vizsgaremek.domain.Project;
import hu.progmasters.vizsgaremek.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class EmployeeControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private EmployeeCreateCommand employeeCreateCommand;
    private EmployeeInfo employeeInfo;
    private EmployeeInfo employeeInfoUpdated;

    private ProjectCreateCommand projectCreateCommand;
    private ProjectInfo projectInfo;
    private ProjectInfo projectInfoUpdated;

    @BeforeEach
    void initAll() {
        ModelMapper modelMapper = new ModelMapper();
        createTestData(modelMapper);
    }

    void createTestData(ModelMapper modelMapper) {

        projectCreateCommand = new ProjectCreateCommand();
        projectCreateCommand.setName("Starship");
        projectCreateCommand.setDescription("I thought it would be funny to make it more pointy, so we did.");

        projectInfo = modelMapper.map(projectCreateCommand, ProjectInfo.class);
        projectInfo.setId(1);

        projectInfoUpdated = modelMapper.map(projectCreateCommand, ProjectInfo.class);
        projectInfoUpdated.setId(1);
        projectInfoUpdated.setTotalWorkHours(9.5);

        employeeCreateCommand = new EmployeeCreateCommand();
        employeeCreateCommand.setName("Elon Musk");

        employeeInfo = modelMapper.map(employeeCreateCommand, EmployeeInfo.class);
        employeeInfo.setId(1);

        employeeInfoUpdated = modelMapper.map(employeeCreateCommand, EmployeeInfo.class);
        employeeInfoUpdated.setId(1);
        employeeInfoUpdated.setBookedHours(9.5);
        employeeInfoUpdated.setWorkedDays(1);
        employeeInfoUpdated.setProjects(List.of(modelMapper.map(projectInfo, Project.class)));

    }

    @Test
    void testList_emptyDatabase_emptyList() throws Exception {
        getEmptyList();
    }

    @Test
    void testFind_invalidId_NotFound() throws Exception {
        employeeNotFound();
    }

    @Test
    void testSave_validCommand_validResponse() throws Exception {
        createValidEmployee();
    }


    @Test
    void testDelete_invalidId_notFoundResponse() throws Exception {
        mockMvc.perform(delete("/api/employees/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDelete_validId_validDeletedResponse() throws Exception {
        createValidEmployee();

        mockMvc.perform(delete("/api/employees/1"))
                .andExpect(status().isOk());
    }

    private void createValidEmployee() throws Exception {
        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeCreateCommand)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(employeeInfo)));
    }

    private void employeeNotFound() throws Exception {
        mockMvc.perform(get("/api/employees/1"))
                .andExpect(status().isNotFound());
    }

    private void getEmptyList() throws Exception {
        mockMvc.perform(get("/api/employees"))
                .andExpect((status().isOk()))
                .andExpect(content().json("[]"));
    }
}


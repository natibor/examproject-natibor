package hu.progmasters.vizsgaremek.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.progmasters.vizsgaremek.dto.ProjectCreateCommand;
import hu.progmasters.vizsgaremek.dto.ProjectInfo;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ProjectControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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

    }

    @Test
    void testList_emptyDatabase_emptyList() throws Exception {
        getEmptyList();
    }

    @Test
    void testFind_invalidId_NotFound() throws Exception {
        projectNotFound();
    }

    @Test
    void testSave_validCommand_validResponse() throws Exception {
        createValidProject();
    }


    @Test
    void testDelete_invalidId_notFoundResponse() throws Exception {
        mockMvc.perform(delete("/api/projects/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDelete_validId_validDeletedResponse() throws Exception {
        createValidProject();

        mockMvc.perform(delete("/api/projects/1"))
                .andExpect(status().isOk());
    }


    private void createValidProject() throws Exception {
        mockMvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectCreateCommand)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(projectInfo)));
    }

    private void projectNotFound() throws Exception {
        mockMvc.perform(get("/api/projects/1"))
                .andExpect(status().isNotFound());
    }

    private void getEmptyList() throws Exception {
        mockMvc.perform(get("/api/projects"))
                .andExpect((status().isOk()))
                .andExpect(content().json("[]"));
    }
}



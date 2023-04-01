package com.damb.taskmanagment.controller;

import com.damb.taskmanagment.domain.Status;
import com.damb.taskmanagment.domain.Task;
import com.damb.taskmanagment.domain.User;
import com.damb.taskmanagment.dto.TaskDTO;
import com.damb.taskmanagment.service.tasks.TaskService;
import com.google.gson.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TaskControllerTest {

    private final static Long MOCK_AUTHOR_ID = 1L;
    private final static Long MOCK_EXECUTOR_ID = 2L;
    private final static String TASK_CODE = "mock-code";
    private final static String TASK_NAME = "mock name";
    private final static Status TASK_STATUS = Status.OPEN;
    private final User mockAuthor;
    private final User mockExecutor;
    private TaskDTO mockTaskDTO;
    private Task mockTask;


    private MockMvc mockMvc;
    private TaskService mockTaskService;
    private Gson gson;

    public TaskControllerTest() {
        this.mockAuthor = new User();
        this.mockExecutor = new User();
        configureMockUser(mockAuthor, MOCK_AUTHOR_ID);
        configureMockUser(mockExecutor, MOCK_EXECUTOR_ID);


        this.gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
            @Override
            public LocalDateTime deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                return ZonedDateTime.parse(json.getAsJsonPrimitive().getAsString()).toLocalDateTime();
            }

        }).create();
        this.mockTaskService = mock(TaskService.class);
        this.mockMvc = MockMvcBuilders.standaloneSetup(new TaskController(mockTaskService)).build();
    }

    @BeforeEach
    void setUp() {
        this.mockTask = new Task();
        configureMockTask(mockTask);
        this.mockTaskDTO = new TaskDTO(TASK_CODE, TASK_NAME, MOCK_AUTHOR_ID, MOCK_EXECUTOR_ID, TASK_STATUS);
    }

    @Test
    void createTaskTest() throws Exception {
        String dtoTaskJson = gson.toJson(mockTaskDTO);
        when(mockTaskService.createTask(mockTaskDTO)).thenReturn(mockTask);
        this.mockMvc
                .perform(post("/api/v1/task/create")
                        .content(dtoTaskJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void setExecutorOnTaskTest() throws Exception {
        String dtoTaskJson = gson.toJson(mockTaskDTO);
        when(mockTaskService.setExecutor(MOCK_EXECUTOR_ID, 1L)).thenReturn(mockTask);
        this.mockMvc
                .perform(patch("/api/v1/task/1/appoint-executor")
                        .content(dtoTaskJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void changeStatusOnTaskTest() throws Exception {
        String dtoTaskJson = gson.toJson(mockTaskDTO);
        when(mockTaskService.changeStatus(Status.CLOSED, 1L)).thenReturn(mockTask);
        this.mockMvc
                .perform(patch("/api/v1/task/1/change-status")
                        .content(dtoTaskJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getTaskByCodeTest() throws Exception {
        when(mockTaskService.getTaskByCode(TASK_CODE)).thenReturn(mockTask);
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/task/" + TASK_CODE))
                .andExpect(status().isOk());
    }

    @Test
    void getTasksByExecutorTest() throws Exception {
        when(mockTaskService.getTasksByExecutor(MOCK_EXECUTOR_ID)).thenReturn(List.of(mockTask));
        this.mockMvc
                .perform(get("/api/v1/task/by-executor").param("id", MOCK_EXECUTOR_ID.toString()))
                .andExpect(status().isOk());
    }

    @Test
    void getTasksByAuthorTest() throws Exception {
        when(mockTaskService.getTasksByAuthor(MOCK_AUTHOR_ID)).thenReturn(List.of(mockTask));
        this.mockMvc
                .perform(get("/api/v1/task/by-author").param("id", MOCK_AUTHOR_ID.toString()))
                .andExpect(status().isOk());
    }

    @Test
    void getAllTasksTest() throws Exception {
        when(mockTaskService.getAllTasks()).thenReturn(List.of(mockTask));
        this.mockMvc
                .perform(get("/api/v1/task/"))
                .andExpect(status().isOk());
    }

    private void configureMockUser(User user, Long mockAuthorId) {
        user.setId(mockAuthorId);
        user.setUsername("mock");
    }

    private void configureMockTask(Task task) {
        task.setId(1L);
        task.setCode(TASK_CODE);
        task.setName(TASK_NAME);
        task.setStatus(TASK_STATUS);
        task.setAuthor(mockAuthor);
        task.setExecutor(mockAuthor);
    }
}
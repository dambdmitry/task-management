package com.damb.taskmanagment.service.tasks.impl;

import com.damb.taskmanagment.converter.TaskDTOConverter;
import com.damb.taskmanagment.domain.Status;
import com.damb.taskmanagment.domain.Task;
import com.damb.taskmanagment.domain.User;
import com.damb.taskmanagment.dto.TaskDTO;
import com.damb.taskmanagment.repository.TaskRepository;
import com.damb.taskmanagment.repository.UserRepository;
import com.damb.taskmanagment.service.tasks.TaskService;
import com.damb.taskmanagment.service.tasks.exceptions.TaskNotFoundException;
import com.damb.taskmanagment.service.users.exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RepositoryTaskServiceTest {
    private final static Long MOCK_AUTHOR_ID = 1L;
    private final static Long MOCK_EXECUTOR_ID = 2L;
    private final static String TASK_CODE = "mock code";
    private final static String TASK_NAME = "mock name";
    private final static Status TASK_STATUS = Status.OPEN;
    private final static Long TASK_ID = 1L;

    private final User mockAuthor;
    private final User mockExecutor;

    private TaskDTO taskDTO;
    private Task task;

    private TaskService taskService;
    private TaskRepository mockTaskRepository;
    private UserRepository mockUserRepository;
    private TaskDTOConverter mockConverter;


    public RepositoryTaskServiceTest() {
        this.mockAuthor = new User();
        mockAuthor.setId(MOCK_AUTHOR_ID);
        this.mockExecutor = new User();
        mockExecutor.setId(MOCK_EXECUTOR_ID);
        this.mockTaskRepository = mock(TaskRepository.class);
        this.mockUserRepository = mock(UserRepository.class);
        this.mockConverter = mock(TaskDTOConverter.class);
        this.taskService = new RepositoryTaskService(mockTaskRepository, mockUserRepository, mockConverter);
    }

    @BeforeEach
    void setUp() {
        this.taskDTO = new TaskDTO(TASK_CODE, TASK_NAME, MOCK_AUTHOR_ID, MOCK_EXECUTOR_ID, TASK_STATUS);
        this.task = new Task();
        configureTask(task);
    }

    private void configureTask(Task task) {
        task.setId(TASK_ID);
        task.setCode(TASK_CODE);
        task.setName(TASK_NAME);
        task.setAuthor(mockAuthor);
        task.setExecutor(mockExecutor);
        task.setStatus(TASK_STATUS);
    }

    @Test
    void createTaskTest() {
        when(mockConverter.convertTaskFromDTO(taskDTO)).thenReturn(task);
        when(mockTaskRepository.save(task)).thenReturn(task);
        Task createdTask = taskService.createTask(taskDTO);
        assertEquals(taskDTO.getStatus(), createdTask.getStatus());
        assertEquals(taskDTO.getCode(), createdTask.getCode());
        assertEquals(taskDTO.getName(), createdTask.getName());
        assertEquals(taskDTO.getAuthorId(), createdTask.getAuthor().getId());
        assertEquals(taskDTO.getExecutorId(), createdTask.getExecutor().getId());
    }

    @Test
    void setExecutorTest() {
        when(mockUserRepository.findById(MOCK_EXECUTOR_ID)).thenReturn(Optional.of(mockExecutor));
        task.setExecutor(null);
        when(mockTaskRepository.findById(TASK_ID)).thenReturn(Optional.of(task));
        when(mockTaskRepository.save(task)).thenReturn(task);
        Task changedTask = taskService.setExecutor(MOCK_EXECUTOR_ID, TASK_ID);
        assertNotNull(changedTask.getExecutor());
        assertEquals(MOCK_EXECUTOR_ID, changedTask.getExecutor().getId());
        assertEquals(MOCK_EXECUTOR_ID, task.getExecutor().getId());
    }

    @Test
    void setExecutorWithNonExistsUserTest() {
        when(mockUserRepository.findById(MOCK_EXECUTOR_ID)).thenReturn(Optional.empty());
        when(mockTaskRepository.findById(TASK_ID)).thenReturn(Optional.of(task));
        when(mockTaskRepository.save(task)).thenReturn(task);
        assertThrows(UserNotFoundException.class, () -> taskService.setExecutor(MOCK_EXECUTOR_ID, TASK_ID));
    }

    @Test
    void setExecutorWithNonExistsTaskTest() {
        when(mockUserRepository.findById(MOCK_EXECUTOR_ID)).thenReturn(Optional.of(mockExecutor));
        when(mockTaskRepository.findById(TASK_ID)).thenReturn(Optional.empty());
        when(mockTaskRepository.save(task)).thenReturn(task);
        assertThrows(TaskNotFoundException.class, () -> taskService.setExecutor(MOCK_EXECUTOR_ID, TASK_ID));
    }

    @Test
    void changeStatusTest() {
        task.setStatus(null);
        when(mockTaskRepository.findById(TASK_ID)).thenReturn(Optional.of(task));
        when(mockTaskRepository.save(task)).thenReturn(task);
        Task changedTask = taskService.changeStatus(TASK_STATUS, TASK_ID);
        assertEquals(TASK_STATUS, changedTask.getStatus());
        assertEquals(TASK_STATUS, task.getStatus());
    }

    @Test
    void changeStatusWithNonExistsTaskTest() {
        task.setStatus(null);
        when(mockTaskRepository.findById(TASK_ID)).thenReturn(Optional.empty());
        when(mockTaskRepository.save(task)).thenReturn(task);
        assertThrows(TaskNotFoundException.class, () -> taskService.changeStatus(TASK_STATUS, TASK_ID));
    }

    @Test
    void getTaskByCodeTest() {
        when(mockTaskRepository.findTaskByCode(TASK_CODE)).thenReturn(Optional.of(task));
        Task foundTask = taskService.getTaskByCode(TASK_CODE);
        assertEquals(TASK_CODE, foundTask.getCode());
    }

    @Test
    void getTaskByCodeNotFoundTest() {
        when(mockTaskRepository.findTaskByCode(TASK_CODE)).thenReturn(Optional.empty());
        assertThrows(TaskNotFoundException.class, () -> taskService.getTaskByCode(TASK_CODE));
    }

    @Test
    void getTaskByExecutorTest() {
        when(mockUserRepository.findById(MOCK_EXECUTOR_ID)).thenReturn(Optional.of(mockExecutor));
        when(mockTaskRepository.findAllByExecutor(mockExecutor)).thenReturn(List.of(task));
        List<Task> tasksByExecutor = taskService.getTasksByExecutor(MOCK_EXECUTOR_ID);
        assertEquals(1, tasksByExecutor.size());
        assertEquals(MOCK_EXECUTOR_ID, tasksByExecutor.get(0).getExecutor().getId());
    }

    @Test
    void getTaskByExecutorWithNonExistsUserTest() {
        when(mockUserRepository.findById(MOCK_EXECUTOR_ID)).thenReturn(Optional.empty());
        when(mockTaskRepository.findAllByExecutor(mockExecutor)).thenReturn(List.of(task));
        assertThrows(UserNotFoundException.class, () -> taskService.getTasksByExecutor(MOCK_EXECUTOR_ID));
    }

    @Test
    void getTaskByAuthorTest() {
        when(mockUserRepository.findById(MOCK_AUTHOR_ID)).thenReturn(Optional.of(mockAuthor));
        when(mockTaskRepository.findAllByAuthor(mockAuthor)).thenReturn(List.of(task));
        List<Task> tasksByAuthor = taskService.getTasksByAuthor(MOCK_AUTHOR_ID);
        assertEquals(1, tasksByAuthor.size());
        assertEquals(MOCK_AUTHOR_ID, tasksByAuthor.get(0).getAuthor().getId());
    }

    @Test
    void getTaskByAuthorWithNonExistsUserTest() {
        when(mockUserRepository.findById(MOCK_AUTHOR_ID)).thenReturn(Optional.empty());
        when(mockTaskRepository.findAllByAuthor(mockAuthor)).thenReturn(List.of(task));
        assertThrows(UserNotFoundException.class, () -> taskService.getTasksByAuthor(MOCK_AUTHOR_ID));
    }

    @Test
    void getAllTasks() {
        when(mockTaskRepository.findAll()).thenReturn(List.of(task));
        List<Task> allTasks = taskService.getAllTasks();
        assertEquals(1, allTasks.size());
    }
}
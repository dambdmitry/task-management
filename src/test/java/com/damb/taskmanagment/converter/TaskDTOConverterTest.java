package com.damb.taskmanagment.converter;

import com.damb.taskmanagment.domain.Status;
import com.damb.taskmanagment.domain.Task;
import com.damb.taskmanagment.domain.User;
import com.damb.taskmanagment.dto.TaskDTO;
import com.damb.taskmanagment.repository.UserRepository;
import com.damb.taskmanagment.service.users.exceptions.UserNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TaskDTOConverterTest {
    private final static Long MOCK_AUTHOR_ID = 1L;
    private final static Long MOCK_EXECUTOR_ID = 2L;
    private final static String TASK_CODE = "mock code";
    private final static String TASK_NAME = "mock name";
    private final static Status TASK_STATUS = Status.OPEN;
    private final User mockAuthor;
    private final User mockExecutor;
    private final TaskDTO mockTaskDTO;
    private final Task mockTask;

    private UserRepository mockUserRepository;
    private TaskDTOConverter converter;

    public TaskDTOConverterTest() {
        this.mockAuthor = new User();
        this.mockExecutor = new User();
        configureMockUser(mockAuthor, MOCK_AUTHOR_ID);
        configureMockUser(mockExecutor, MOCK_EXECUTOR_ID);
        this.mockTask = new Task();
        configureMockTask(mockTask);
        this.mockTaskDTO = new TaskDTO(TASK_CODE, TASK_NAME, MOCK_AUTHOR_ID, MOCK_EXECUTOR_ID, TASK_STATUS);
        this.mockUserRepository = mock(UserRepository.class);
        this.converter = new TaskDTOConverter(mockUserRepository);
    }

    @Test
    void convertTaskFromDTOTest() {
        when(mockUserRepository.findById(MOCK_AUTHOR_ID)).thenReturn(Optional.of(mockAuthor));
        when(mockUserRepository.findById(MOCK_EXECUTOR_ID)).thenReturn(Optional.of(mockExecutor));
        Task task = converter.convertTaskFromDTO(mockTaskDTO);
        assertEquals(mockTaskDTO.getCode(), task.getCode());
        assertEquals(mockTaskDTO.getName(), task.getName());
        assertEquals(mockTaskDTO.getStatus(), task.getStatus());
        assertEquals(MOCK_AUTHOR_ID, task.getAuthor().getId());
        assertEquals(MOCK_EXECUTOR_ID, task.getExecutor().getId());
    }

    @Test
    void convertTaskFromDTONotFoundAuthorTest() {
        when(mockUserRepository.findById(MOCK_AUTHOR_ID)).thenReturn(Optional.empty());
        when(mockUserRepository.findById(MOCK_EXECUTOR_ID)).thenReturn(Optional.of(mockExecutor));
        assertThrows(UserNotFoundException.class, () -> converter.convertTaskFromDTO(mockTaskDTO));
    }

    @Test
    void convertTaskFromDTONotFoundExecutorTest() {
        when(mockUserRepository.findById(MOCK_AUTHOR_ID)).thenReturn(Optional.of(mockAuthor));
        when(mockUserRepository.findById(MOCK_EXECUTOR_ID)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> converter.convertTaskFromDTO(mockTaskDTO));
    }


    private void configureMockUser(User user, Long mockAuthorId) {
        user.setId(mockAuthorId);
        user.setUsername("mock");
    }

    private void configureMockTask(Task task) {
        task.setCode(TASK_CODE);
        task.setName(TASK_NAME);
        task.setStatus(TASK_STATUS);
        task.setAuthor(mockAuthor);
        task.setExecutor(mockAuthor);
    }
}
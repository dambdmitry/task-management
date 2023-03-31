package com.damb.taskmanagment.converter;

import com.damb.taskmanagment.domain.Task;
import com.damb.taskmanagment.domain.User;
import com.damb.taskmanagment.dto.TaskDTO;
import com.damb.taskmanagment.repository.UserRepository;
import com.damb.taskmanagment.service.users.exceptions.UserNotFoundException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class TaskDTOConverter {
    private UserRepository repository;

    public TaskDTOConverter(UserRepository repository) {
        this.repository = repository;
    }

    public Task convertTaskFromDTO(TaskDTO dto) {
        Task task = new Task();
        task.setCode(dto.getCode());
        task.setName(dto.getName());
        setAuthorToTask(task, dto.getAuthorId());
        setExecutorToTaskIfExists(task, dto.getExecutorId());
        task.setStatus(dto.getStatus());
        task.setCreated(LocalDateTime.now());
        return task;
    }

    private void setAuthorToTask(Task task, Long authorId) {
        Optional<User> author = repository.findById(authorId);
        task.setAuthor(author
                .orElseThrow(() -> new UserNotFoundException(String.format("user with id %d not found", authorId))));
    }

    private void setExecutorToTaskIfExists(Task task, Long executorId) {
        if (executorId != null) {
            Optional<User> executor = repository.findById(executorId);
            task.setExecutor(executor
                    .orElseThrow(() -> new UserNotFoundException(String.format("user with id %d not found", executorId))));
        }
    }
}

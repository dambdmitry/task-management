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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RepositoryTaskService implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskDTOConverter converter;

    public RepositoryTaskService(TaskRepository taskRepository, UserRepository userRepository, TaskDTOConverter converter) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.converter = converter;
    }

    @Override
    public Task createTask(TaskDTO taskDTO) {
        Task convertedTask = converter.convertTaskFromDTO(taskDTO);
        return taskRepository.save(convertedTask);
    }

    @Override
    public Task setExecutor(Long executorId, Long taskId) {
        User newExecutor = getUserById(executorId);
        Task taskToChange = getTaskById(taskId);
        taskToChange.setExecutor(newExecutor);
        return taskRepository.save(taskToChange);
    }

    @Override
    public Task changeStatus(Status newStatus, Long taskId) {
        Task taskToChange = getTaskById(taskId);
        taskToChange.setStatus(newStatus);
        return taskRepository.save(taskToChange);
    }

    @Override
    public Task getTaskByCode(String code) {
        Optional<Task> foundTask = taskRepository.findTaskByCode(code);
        return foundTask.orElseThrow(() -> new TaskNotFoundException(String.format("task with code %s not found", code)));
    }

    @Override
    public List<Task> getTasksByExecutor(Long executorId) {
        User foundExecutor = getUserById(executorId);
        return taskRepository.findAllByExecutor(foundExecutor);
    }

    @Override
    public List<Task> getTasksByAuthor(Long authorId) {
        User foundAuthor = getUserById(authorId);
        return taskRepository.findAllByAuthor(foundAuthor);
    }

    @Override
    public List<Task> getAllTasks() {
        return (List<Task>) taskRepository.findAll();
    }

    private Task getTaskById(Long id) {
        Optional<Task> foundTask = this.taskRepository.findById(id);
        return foundTask.orElseThrow(() -> new TaskNotFoundException(String.format("task with id %d not found", id)));
    }

    private User getUserById(Long id) {
        Optional<User> foundUser = userRepository.findById(id);
        return foundUser.orElseThrow(() -> new UserNotFoundException(String.format("user with id %d not found", id)));
    }
}

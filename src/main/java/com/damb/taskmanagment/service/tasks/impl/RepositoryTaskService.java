package com.damb.taskmanagment.service.tasks.impl;

import com.damb.taskmanagment.domain.Status;
import com.damb.taskmanagment.domain.Task;
import com.damb.taskmanagment.dto.TaskDTO;
import com.damb.taskmanagment.repository.TaskRepository;
import com.damb.taskmanagment.repository.UserRepository;
import com.damb.taskmanagment.service.tasks.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepositoryTaskService implements TaskService {

    private TaskRepository taskRepository;
    private UserRepository userRepository;

    public RepositoryTaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Task createTask(TaskDTO taskDTO) {
        return null;
    }

    @Override
    public Task setExecutor(Long executorId, Long taskId) {
        return null;
    }

    @Override
    public Task changeStatus(Status newStatus, Long taskId) {
        return null;
    }

    @Override
    public Task getTaskByCode(String code) {
        return null;
    }

    @Override
    public List<Task> getTaskByExecutor(Long executorId) {
        return null;
    }

    @Override
    public List<Task> getTaskByAuthor(Long authorId) {
        return null;
    }

    @Override
    public List<Task> getAllTasks() {
        return null;
    }
}

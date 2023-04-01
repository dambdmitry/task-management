package com.damb.taskmanagment.service.tasks;

import com.damb.taskmanagment.domain.Status;
import com.damb.taskmanagment.domain.Task;
import com.damb.taskmanagment.dto.TaskDTO;

import java.util.List;

public interface TaskService {
    Task createTask(TaskDTO taskDTO);

    Task setExecutor(Long executorId, Long taskId);

    Task changeStatus(Status newStatus, Long taskId);

    Task getTaskByCode(String code);

    List<Task> getTasksByExecutor(Long executorId);

    List<Task> getTasksByAuthor(Long authorId);

    List<Task> getAllTasks();
}

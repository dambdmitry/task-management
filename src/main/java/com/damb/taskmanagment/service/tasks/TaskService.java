package com.damb.taskmanagment.service.tasks;

import com.damb.taskmanagment.domain.Status;
import com.damb.taskmanagment.domain.Task;
import com.damb.taskmanagment.dto.TaskDTO;
import com.damb.taskmanagment.dto.UserDTO;

import java.util.List;

public interface TaskService {
    Task createTask(TaskDTO taskDTO);

    Task setExecutor(Long executorId, Long taskId);

    Task changeStatus(Status newStatus, Long taskId);

    Task getTaskByCode(String code);

    List<Task> getTaskByExecutor(Long executorId);

    List<Task> getTaskByAuthor(Long authorId);

    List<Task> getAllTasks();
}

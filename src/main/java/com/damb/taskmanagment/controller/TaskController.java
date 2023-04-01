package com.damb.taskmanagment.controller;

import com.damb.taskmanagment.domain.Task;
import com.damb.taskmanagment.dto.TaskDTO;
import com.damb.taskmanagment.service.tasks.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/task")
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/create", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Task> createTask(@RequestBody TaskDTO dto) {
        Task task = service.createTask(dto);
        return ResponseEntity.created(URI.create(String.format("/api/v1/task/%d", task.getId()))).body(task);
    }

    @RequestMapping(method = RequestMethod.PATCH, path = "/{id}/appoint-executor")
    public ResponseEntity<Task> setExecutorOnTask(@RequestBody TaskDTO dto, @PathVariable Long id) {
        return ResponseEntity.ok(service.setExecutor(dto.getExecutorId(), id));
    }

    @RequestMapping(method = RequestMethod.PATCH, path = "/{id}/change-status")
    public ResponseEntity<Task> changeStatusOnTask(@RequestBody TaskDTO dto, @PathVariable Long id){
        return ResponseEntity.ok(service.changeStatus(dto.getStatus(), id));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{code}")
    public ResponseEntity<Task> getTaskByCode(@PathVariable String code) {
        return ResponseEntity.ok(service.getTaskByCode(code));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/by-executor")
    public ResponseEntity<List<Task>> getTasksByExecutor(@RequestParam(name = "id") Long id) {
        return ResponseEntity.ok(service.getTasksByExecutor(id));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/by-author")
    public ResponseEntity<List<Task>> getTasksByAuthor(@RequestParam(name = "id") Long id) {
        return ResponseEntity.ok(service.getTasksByAuthor(id));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/")
    public ResponseEntity<List<Task>> getAllTasks() {
        return ResponseEntity.ok(service.getAllTasks());
    }
}

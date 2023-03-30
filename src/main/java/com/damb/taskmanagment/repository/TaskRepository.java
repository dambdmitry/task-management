package com.damb.taskmanagment.repository;

import com.damb.taskmanagment.domain.Task;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Long> {
}

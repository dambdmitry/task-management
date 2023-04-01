package com.damb.taskmanagment.repository;

import com.damb.taskmanagment.domain.Task;
import com.damb.taskmanagment.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends CrudRepository<Task, Long> {
    Optional<Task> findTaskByCode(String code);

    List<Task> findAllByExecutor(User executor);

    List<Task> findAllByAuthor(User author);
}

package com.damb.taskmanagment.repository;

import com.damb.taskmanagment.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}

package com.damb.taskmanagment.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

import java.time.LocalDateTime;

@Entity(name = "tasks")
@Data
public class Task {
    @Id
    private Long id;
    private String code;
    private String name;
    @OneToOne(optional = false)
    @JoinColumn(name = "id")
    private User author;
    @OneToOne
    @JoinColumn(name = "id")
    private User executor;
    private Status status;
    private LocalDateTime created;

}

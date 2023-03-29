package com.damb.taskmanagment.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Task {
    @Id
    private Long id;
    private String code;
    private String name;
    @OneToOne(mappedBy = "id", optional = false)
    private User author;
    @OneToOne(mappedBy = "id")
    private User executor;
    private Status status;
    private LocalDateTime timeToExecute;

}

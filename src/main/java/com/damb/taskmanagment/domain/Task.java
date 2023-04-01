package com.damb.taskmanagment.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity(name = "tasks")
@Data
public class Task {
    @Id
    @GeneratedValue
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

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }
}

package com.damb.taskmanagment.dto;

import com.damb.taskmanagment.domain.Status;
import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO implements Serializable {
//    @Nonnull
    private String code;
    private String name;
//    @Nonnull
    private Long authorId;
    private Long executorId;
    private Status status;

}

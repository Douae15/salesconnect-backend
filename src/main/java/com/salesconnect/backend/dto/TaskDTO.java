package com.salesconnect.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDTO {

    private Long taskId;
    private String subject;
    private String description;
    private String type;
    private LocalDateTime dueDate;
    private String status;
    private Long userId;
}

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
public class ActivityDTO {

    private Long activityId;
    private String type;
    private String summary;
    private LocalDateTime date;
    private Long userId;
    private Long contactId;
}


package com.victorlicht.novenabackend.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShiftDto {

    private Long id;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Long doctorId;
}

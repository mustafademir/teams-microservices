package com.wynnbet.teamservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PlayerDto {
    private Long id;
    private Long teamId;
    private String name;
    private LocalDateTime startedDate;
    private LocalDateTime dateOfBirth;
}

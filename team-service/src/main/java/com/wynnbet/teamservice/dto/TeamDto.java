package com.wynnbet.teamservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TeamDto {
    private Long id;
    private String name;
    private String currency;
    private Long fund;
    private List<PlayerDto> playerDtoList;
}

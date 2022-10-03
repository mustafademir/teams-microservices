package com.wynnbet.teamservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TransferDto {
    private Long playerId;
    private Long sourceTeamId;
    private Long destinationTeamId;
    private Long fee;
}

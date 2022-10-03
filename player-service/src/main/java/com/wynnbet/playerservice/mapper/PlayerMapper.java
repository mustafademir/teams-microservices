package com.wynnbet.playerservice.mapper;

import com.wynnbet.playerservice.dto.PlayerDto;
import com.wynnbet.playerservice.entity.Player;
import org.springframework.stereotype.Component;

@Component
public class PlayerMapper {
    public PlayerDto entityToDto(Player player) {
        return PlayerDto.builder()
                .id(player.getId())
                .name(player.getName())
                .teamId(player.getTeamId())
                .dateOfBirth(player.getDateOfBirth())
                .startedDate(player.getStartedDate())
                .build();
    }
}

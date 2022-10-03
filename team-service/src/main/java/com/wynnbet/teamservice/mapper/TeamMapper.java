package com.wynnbet.teamservice.mapper;

import com.wynnbet.teamservice.dto.TeamDto;
import com.wynnbet.teamservice.entity.Team;
import org.springframework.stereotype.Component;

@Component
public class TeamMapper {
    public TeamDto entityToDto(Team team) {
        return TeamDto.builder().id(team.getId())
                .name(team.getName())
                .currency(team.getCurrency())
                .fund(team.getFund())
                .build();
    }
}

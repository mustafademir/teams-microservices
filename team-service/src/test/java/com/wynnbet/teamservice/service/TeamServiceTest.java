package com.wynnbet.teamservice.service;

import com.wynnbet.teamservice.client.PlayerClient;
import com.wynnbet.teamservice.dto.PlayerDto;
import com.wynnbet.teamservice.dto.TeamDto;
import com.wynnbet.teamservice.dto.TransferDto;
import com.wynnbet.teamservice.entity.Team;
import com.wynnbet.teamservice.mapper.TeamMapper;
import com.wynnbet.teamservice.repository.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


class TeamServiceTest {
    @Mock
    TeamRepository teamRepository;

    @Mock
    TeamMapper teamMapper;

    @Mock
    PlayerClient playerClient;

    @InjectMocks
    TeamService teamService;

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void test_create() {
        TeamDto teamDto = generateTeamDto();
        when(teamRepository.save(any(Team.class))).thenReturn(new Team());
        when(teamMapper.entityToDto(any(Team.class))).thenReturn(teamDto);
        assertEquals("gs", teamService.create(teamDto).getName());
    }

    @Test
    void test_delete() {
        doNothing().when(teamRepository).deleteById(anyLong());
        teamService.delete(1L);
        verify(teamRepository,times(1)).deleteById(anyLong());
    }

    @Test
    void test_update() {
        Team team = generateTeam();
        when(teamRepository.findById(anyLong())).thenReturn(Optional.of(team));
        when(teamRepository.save(any(Team.class))).thenReturn(team);
        when(teamMapper.entityToDto(any(Team.class))).thenReturn(generateTeamDto());
        assertEquals("FCB", teamService.update(new TeamDto(),1L).getName());
    }

    @Test
    void test_get() {
        Team team = generateTeam();
        when(teamRepository.findById(anyLong())).thenReturn(Optional.of(team));
        when(playerClient.getPlayers(anyLong())).thenReturn(ResponseEntity.ok(Collections.singletonList(generatePlayerDto())));
        when(teamMapper.entityToDto(any(Team.class))).thenReturn(generateTeamDto());
        assertEquals("FCB", teamService.get(1L).getName());
    }

    @Test
    void test_transfer() {
        Team sourceTeam = generateTeam();
        Team destinationTeam = generateTeam();
        destinationTeam.setId(2L);
        when(teamRepository.findById(anyLong())).thenReturn(Optional.of(sourceTeam),Optional.of(sourceTeam));
        when(playerClient.getPlayer(anyLong())).thenReturn(ResponseEntity.ok(generatePlayerDto()));
        when(playerClient.updatePlayer(any(PlayerDto.class),anyLong())).thenReturn(ResponseEntity.ok(generatePlayerDto()));
        when(teamMapper.entityToDto(any(Team.class))).thenReturn(generateTeamDto());
        assertTrue(teamService.transfer(generateTransferDto()));
    }

    private TeamDto generateTeamDto() {
        return TeamDto.builder().currency("TRY")
                .name("FCB")
                .fund(10000.0)
                .build();
    }

    private Team generateTeam() {
        return Team.builder().id(1L)
                .fund(10000.0)
                .currency("TRY")
                .name("FCB").build();
    }

    private PlayerDto generatePlayerDto() {
        return PlayerDto.builder().id(1L)
                .name("alex")
                .dateOfBirth(LocalDateTime.of(2000,5,3,4,5,2))
                .startedDate(LocalDateTime.of(2008,1,1,2,4,5))
                .teamId(1L)
                .build();
    }

    private TransferDto generateTransferDto() {
        return TransferDto.builder().sourceTeamId(1L)
                .destinationTeamId(2L)
                .fee(1000L)
                .playerId(1L)
                .build();
    }
}

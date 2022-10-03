package com.wynnbet.playerservice.service;

import com.wynnbet.playerservice.dto.PlayerDto;
import com.wynnbet.playerservice.entity.Player;
import com.wynnbet.playerservice.mapper.PlayerMapper;
import com.wynnbet.playerservice.repository.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class PlayerServiceTest {

    @Mock
    PlayerRepository playerRepository;

    @Mock
    PlayerMapper playerMapper;

    @InjectMocks
    PlayerService playerService;

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void test_create() {
        PlayerDto teamDto = generatePlayerDto();
        when(playerRepository.save(any(Player.class))).thenReturn(new Player());
        when(playerMapper.entityToDto(any(Player.class))).thenReturn(teamDto);
        assertEquals("alex", playerService.create(teamDto).getName());
    }

    @Test
    void test_delete() {
        doNothing().when(playerRepository).deleteById(anyLong());
        playerService.delete(1L);
        verify(playerRepository,times(1)).deleteById(anyLong());
    }

    @Test
    void test_update() {
        Player player = generatePlayer();
        when(playerRepository.findById(anyLong())).thenReturn(Optional.of(player));
        when(playerRepository.save(any(Player.class))).thenReturn(player);
        when(playerMapper.entityToDto(any(Player.class))).thenReturn(generatePlayerDto());
        assertEquals("alex", playerService.update(new PlayerDto(),1L).getName());
    }

    @Test
    void test_get() {
        Player player = generatePlayer();
        when(playerRepository.findById(anyLong())).thenReturn(Optional.of(player));
        when(playerMapper.entityToDto(any(Player.class))).thenReturn(generatePlayerDto());
        assertEquals("alex", playerService.get(1L).getName());
    }

    private PlayerDto generatePlayerDto() {
        return PlayerDto.builder().id(1L)
                .name("alex")
                .dateOfBirth(LocalDateTime.of(2000,5,3,4,5,2))
                .startedDate(LocalDateTime.of(2008,1,1,2,4,5))
                .teamId(1L)
                .build();
    }

    private Player generatePlayer() {
        Player player = new Player();
        player.setId(1L);
        player.setStartedDate(LocalDateTime.of(2008,1,1,2,4,5));
        player.setTeamId(1L);
        player.setDateOfBirth(LocalDateTime.of(2000,5,3,4,5,2));
        player.setName("alex");
        return player;
    }

}

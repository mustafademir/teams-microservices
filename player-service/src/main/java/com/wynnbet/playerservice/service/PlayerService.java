package com.wynnbet.playerservice.service;

import com.wynnbet.playerservice.dto.PlayerDto;
import com.wynnbet.playerservice.entity.Player;
import com.wynnbet.playerservice.exception.EntityNotFoundException;
import com.wynnbet.playerservice.mapper.PlayerMapper;
import com.wynnbet.playerservice.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.wynnbet.playerservice.common.Constants.PLAYER_NOT_FOUND_MESSAGE;

@RequiredArgsConstructor
@Service
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;

    public PlayerDto create(PlayerDto playerDto) {
        Player player = new Player();
        player.setName(playerDto.getName());
        player.setTeamId(playerDto.getTeamId());
        player.setDateOfBirth(playerDto.getDateOfBirth());
        player.setStartedDate(playerDto.getStartedDate());
        return playerMapper.entityToDto(playerRepository.save(player));
    }

    public boolean delete(Long id) {
        playerRepository.deleteById(id);
        return true;
    }

    public PlayerDto update(PlayerDto playerDto, Long id) {
        Optional<Player> optionalPlayer = playerRepository.findById(id);
        if (optionalPlayer.isPresent()) {
            Player player = optionalPlayer.get();
            player.setName(playerDto.getName());
            player.setDateOfBirth(playerDto.getDateOfBirth());
            player.setStartedDate(playerDto.getStartedDate());
            player.setTeamId(playerDto.getTeamId());
            return playerMapper.entityToDto(playerRepository.save(player));
        } else {
            throw new EntityNotFoundException(id + PLAYER_NOT_FOUND_MESSAGE);
        }
    }

    public PlayerDto get(Long id) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id + PLAYER_NOT_FOUND_MESSAGE));
        return playerMapper.entityToDto(player);
    }

    public List<PlayerDto> findByTeam(Long id) {
        return playerRepository.findAllByTeamId(id)
                .stream()
                .map(playerMapper::entityToDto)
                .collect(Collectors.toList());
    }
}

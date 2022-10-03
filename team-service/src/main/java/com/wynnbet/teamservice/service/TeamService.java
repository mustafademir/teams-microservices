package com.wynnbet.teamservice.service;

import com.wynnbet.teamservice.client.PlayerClient;
import com.wynnbet.teamservice.dto.PlayerDto;
import com.wynnbet.teamservice.dto.TeamDto;
import com.wynnbet.teamservice.dto.TransferDto;
import com.wynnbet.teamservice.entity.Team;
import com.wynnbet.teamservice.exception.EntityNotFoundException;
import com.wynnbet.teamservice.mapper.TeamMapper;
import com.wynnbet.teamservice.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Optional;

import static com.wynnbet.teamservice.common.Constants.TEAM_NOT_FOUND_MESSAGE;

@RequiredArgsConstructor
@Service
public class TeamService {
    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;
    private final PlayerClient playerClient;

    public TeamDto create(TeamDto teamDto) {
        Team team = new Team();
        team.setName(teamDto.getName());
        team.setFund(teamDto.getFund());
        team.setCurrency(teamDto.getCurrency());
        return  teamMapper.entityToDto(teamRepository.save(team));
    }

    public boolean delete(Long id) {
        teamRepository.deleteById(id);
        return true;
    }

    public TeamDto update(TeamDto teamDto, Long id) {
        Optional<Team> optionalTeam = teamRepository.findById(id);
        if(optionalTeam.isPresent()) {
            Team team = optionalTeam.get();
            team.setName(teamDto.getName());
            team.setCurrency(teamDto.getCurrency());
            team.setFund(teamDto.getFund());
            return teamMapper.entityToDto(teamRepository.save(team));
        } else {
            throw new EntityNotFoundException(id + TEAM_NOT_FOUND_MESSAGE);
        }
    }

    public TeamDto get(Long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id + TEAM_NOT_FOUND_MESSAGE));
        TeamDto teamDto = teamMapper.entityToDto(team);
        teamDto.setPlayerDtoList(playerClient.getPlayers(id).getBody());
        return teamDto;
    }

    @Transactional
    public boolean transfer(TransferDto transferDto) {
        Team sourceTeam = teamRepository.findById(transferDto.getSourceTeamId())
                .orElseThrow(() -> new EntityNotFoundException(transferDto.getSourceTeamId() + TEAM_NOT_FOUND_MESSAGE));
        Team destinationTeam = teamRepository.findById(transferDto.getDestinationTeamId())
                .orElseThrow(() -> new EntityNotFoundException(transferDto.getDestinationTeamId() + TEAM_NOT_FOUND_MESSAGE));
        PlayerDto player = playerClient.getPlayer(transferDto.getPlayerId()).getBody();
        long transferFee = calculateTransferFee(player);
        Long sourceTeamCommission = transferFee/10;
        Long destinationTeamContractFee = transferFee + sourceTeamCommission;

        sourceTeam.setFund(sourceTeam.getFund() + sourceTeamCommission);
        teamRepository.save(sourceTeam);
        destinationTeam.setFund(sourceTeam.getFund() - destinationTeamContractFee);
        teamRepository.save(destinationTeam);
        Objects.requireNonNull(player).setTeamId(destinationTeam.getId());
        playerClient.updatePlayer(player, player.getId());
        return true;
    }

    private long calculateTransferFee(PlayerDto player) {
        return (calculateMonthsOfExperience(player) * (100000 / calculateAge(player)));
    }

    private long calculateMonthsOfExperience(PlayerDto player) {
        return ChronoUnit.MONTHS.between(player.getStartedDate(), LocalDateTime.now());
    }

    private long calculateAge(PlayerDto player) {
        return ChronoUnit.YEARS.between(player.getDateOfBirth(), LocalDateTime.now());
    }
}

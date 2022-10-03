package com.wynnbet.teamservice.client;

import com.wynnbet.teamservice.dto.PlayerDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Component
@FeignClient("player-service")
public interface PlayerClient {

    @GetMapping("api/player/team/{teamId}")
    ResponseEntity<List<PlayerDto>> getPlayers(@PathVariable("teamId") Long teamId);

    @GetMapping("api/player/{playerId}")
    ResponseEntity<PlayerDto> getPlayer(@PathVariable("playerId") Long playerId);

    @PutMapping("api/player/{playerId}")
    ResponseEntity<PlayerDto> updatePlayer(@RequestBody PlayerDto playerDto, @PathVariable("playerId") Long playerId);
}

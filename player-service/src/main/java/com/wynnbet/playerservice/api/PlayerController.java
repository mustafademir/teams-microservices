package com.wynnbet.playerservice.api;

import com.wynnbet.playerservice.dto.PlayerDto;
import com.wynnbet.playerservice.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.wynnbet.playerservice.common.Constants.BASE;
import static com.wynnbet.playerservice.common.Constants.PLAYER_URI;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = BASE + PLAYER_URI)
public class PlayerController {
    private final PlayerService playerService;

    @PostMapping
    public ResponseEntity<PlayerDto> create(@RequestBody PlayerDto teamDto) {
        return new ResponseEntity<>(playerService.create(teamDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return new ResponseEntity<>(playerService.delete(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlayerDto> update(@RequestBody PlayerDto teamDto,@PathVariable Long id) {
        return new ResponseEntity<>(playerService.update(teamDto,id), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerDto> get(@PathVariable Long id) {
        return new ResponseEntity<>(playerService.get(id), HttpStatus.OK);
    }

    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<PlayerDto>> findByTeam(@PathVariable Long teamId) {
        return new ResponseEntity<>(playerService.findByTeam(teamId), HttpStatus.OK);
    }
}

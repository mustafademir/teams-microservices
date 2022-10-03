package com.wynnbet.teamservice.api;

import com.wynnbet.teamservice.dto.TeamDto;
import com.wynnbet.teamservice.dto.TransferDto;
import com.wynnbet.teamservice.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.wynnbet.teamservice.common.Constants.BASE;
import static com.wynnbet.teamservice.common.Constants.TEAM_URI;


@RequiredArgsConstructor
@RestController
@RequestMapping(value = BASE + TEAM_URI)
public class TeamController {
    private final TeamService teamService;

    @PostMapping
    public ResponseEntity<TeamDto> create(@RequestBody TeamDto teamDto) {
        return new ResponseEntity<>(teamService.create(teamDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return new ResponseEntity<>(teamService.delete(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeamDto> update(@RequestBody TeamDto teamDto,@PathVariable Long id) {
        return new ResponseEntity<>(teamService.update(teamDto,id), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamDto> get(@PathVariable Long id) {
        return new ResponseEntity<>(teamService.get(id), HttpStatus.OK);
    }

    @PostMapping("/transfer")
    public ResponseEntity<Boolean> transfer(@RequestBody TransferDto transferDto) {
        return new ResponseEntity<>(teamService.transfer(transferDto), HttpStatus.OK);
    }
}

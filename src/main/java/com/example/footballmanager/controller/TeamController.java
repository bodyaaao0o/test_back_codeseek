
package com.example.footballmanager.controller;

import com.example.footballmanager.entity.Team;
import com.example.footballmanager.service.TeamService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teams")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping
    public List<Team> getAll() {
        return teamService.getAllTeams();
    }

    @GetMapping("/{id}")
    public Team getById(@PathVariable Long id) {
        return teamService.getTeamById(id);
    }

    @PostMapping
    public Team create(@RequestBody @Valid Team team) {
        return teamService.createTeam(team);
    }

    @PutMapping("/{id}")
    public Team update(@PathVariable Long id, @RequestBody @Valid Team team) {
        return teamService.updateTeam(id, team);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        teamService.deleteTeam(id);
        return ResponseEntity.noContent().build();
    }
}

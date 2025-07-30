
package com.example.footballmanager.service;

import com.example.footballmanager.entity.Team;
import com.example.footballmanager.repository.TeamRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public Team getTeamById(Long id) {
        return teamRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Team not found"));
    }

    public Team createTeam(Team team) {
        return teamRepository.save(team);
    }

    public Team updateTeam(Long id, Team updated) {
        Team team = getTeamById(id);
        team.setName(updated.getName());
        team.setBalance(updated.getBalance());
        team.setCommission(updated.getCommission());
        return teamRepository.save(team);
    }

    public void deleteTeam(Long id) {
        teamRepository.deleteById(id);
    }
}

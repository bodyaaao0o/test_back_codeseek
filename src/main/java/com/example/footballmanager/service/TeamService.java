package com.example.footballmanager.service;

import com.example.footballmanager.dto.TeamDTO;
import com.example.footballmanager.dto.PlayerSummaryDTO;
import com.example.footballmanager.entity.Team;
import com.example.footballmanager.exception.ResourceNotFoundException;
import com.example.footballmanager.exception.ValidationException;
import com.example.footballmanager.repository.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TeamService {

    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Transactional(readOnly = true)
    public List<TeamDTO> getAllTeams() {
        return teamRepository.findAllWithPlayers().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TeamDTO getTeamById(Long id) {
        Team team = teamRepository.findByIdWithPlayers(id)
                .orElseThrow(() -> new ResourceNotFoundException("not found by id: " + id));
        return convertToDTO(team);
    }

    public TeamDTO createTeam(TeamDTO teamDTO) {
        if (teamRepository.existsByName(teamDTO.getName())) {
            throw new ValidationException("team '" + teamDTO.getName() + "' already exists");
        }

        Team team = convertToEntity(teamDTO);
        Team savedTeam = teamRepository.save(team);
        return convertToDTO(savedTeam);
    }

    public TeamDTO updateTeam(Long id, TeamDTO teamDTO) {
        Team existingTeam = teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("not found by id: " + id));

        if (!existingTeam.getName().equals(teamDTO.getName()) &&
                teamRepository.existsByName(teamDTO.getName())) {
            throw new ValidationException("team '" + teamDTO.getName() + "' already exists");
        }

        existingTeam.setName(teamDTO.getName());
        existingTeam.setBalance(teamDTO.getBalance());
        existingTeam.setCommission(teamDTO.getCommission());

        Team updatedTeam = teamRepository.save(existingTeam);
        return convertToDTO(updatedTeam);
    }

    public void deleteTeam(Long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("not found by id: " + id));

        if (team.getPlayers() != null && !team.getPlayers().isEmpty()) {
            throw new ValidationException("cannot delete team with players");
        }

        teamRepository.delete(team);
    }

    // Internal method to get team entity
    @Transactional(readOnly = true)
    public Team getTeamEntityById(Long id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("not found by id: " + id));
    }

    private TeamDTO convertToDTO(Team team) {
        TeamDTO dto = new TeamDTO();
        dto.setId(team.getId());
        dto.setName(team.getName());
        dto.setBalance(team.getBalance());
        dto.setCommission(team.getCommission());

        if (team.getPlayers() != null) {
            List<PlayerSummaryDTO> playerDTOs = team.getPlayers().stream()
                    .map(player -> new PlayerSummaryDTO(
                            player.getId(),
                            player.getName(),
                            player.getAge(),
                            player.getExperienceMonths()))
                    .collect(Collectors.toList());
            dto.setPlayers(playerDTOs);
        }

        return dto;
    }

    private Team convertToEntity(TeamDTO dto) {
        Team team = new Team();
        team.setName(dto.getName());
        team.setBalance(dto.getBalance());
        team.setCommission(dto.getCommission());
        return team;
    }
}
package com.example.footballmanager.service;

import com.example.footballmanager.dto.PlayerDTO;
import com.example.footballmanager.dto.TeamSummaryDTO;
import com.example.footballmanager.entity.Player;
import com.example.footballmanager.entity.Team;
import com.example.footballmanager.exception.BusinessException;
import com.example.footballmanager.exception.ResourceNotFoundException;
import com.example.footballmanager.repository.PlayerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final TeamService teamService;

    public PlayerService(PlayerRepository playerRepository, TeamService teamService) {
        this.playerRepository = playerRepository;
        this.teamService = teamService;
    }

    @Transactional(readOnly = true)
    public List<PlayerDTO> getAllPlayers() {
        return playerRepository.findAllWithTeam().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PlayerDTO getPlayerById(Long id) {
        Player player = playerRepository.findByIdWithTeam(id)
                .orElseThrow(() -> new ResourceNotFoundException("not found by id: " + id));
        return convertToDTO(player);
    }

    public PlayerDTO createPlayer(PlayerDTO playerDTO) {
        Player player = convertToEntity(playerDTO);

        if (playerDTO.getTeam() != null && playerDTO.getTeam().getId() != null) {
            Team team = teamService.getTeamEntityById(playerDTO.getTeam().getId());
            player.setTeam(team);
        }

        Player savedPlayer = playerRepository.save(player);
        return convertToDTO(savedPlayer);
    }

    public PlayerDTO updatePlayer(Long id, PlayerDTO playerDTO) {
        Player existingPlayer = playerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("not found by id: " + id));

        existingPlayer.setName(playerDTO.getName());
        existingPlayer.setAge(playerDTO.getAge());
        existingPlayer.setExperienceMonths(playerDTO.getExperienceMonths());

        if (playerDTO.getTeam() != null && playerDTO.getTeam().getId() != null) {
            Team team = teamService.getTeamEntityById(playerDTO.getTeam().getId());
            existingPlayer.setTeam(team);
        } else {
            existingPlayer.setTeam(null);
        }

        Player updatedPlayer = playerRepository.save(existingPlayer);
        return convertToDTO(updatedPlayer);
    }

    public void deletePlayer(Long id) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("not found by id: " + id));
        playerRepository.delete(player);
    }

    public void transferPlayer(Long playerId, Long targetTeamId) {
        Player player = playerRepository.findByIdWithTeam(playerId)
                .orElseThrow(() -> new ResourceNotFoundException("not found by id: " + playerId));

        Team targetTeam = teamService.getTeamEntityById(targetTeamId);
        Team sourceTeam = player.getTeam();

        if (sourceTeam != null && sourceTeam.getId().equals(targetTeamId)) {
            throw new BusinessException("Player is already in team");
        }

        // Calculate transfer cost
        BigDecimal transferValue = calculateTransferValue(player);
        BigDecimal commission = transferValue.multiply(BigDecimal.valueOf(targetTeam.getCommission()));
        BigDecimal totalCost = transferValue.add(commission);

        // Check if target team has enough balance
        if (targetTeam.getBalance().compareTo(totalCost) < 0) {
            throw new BusinessException("Not enough monet "
                    + totalCost + ", available: " + targetTeam.getBalance());
        }

        // Process the transfer
        targetTeam.setBalance(targetTeam.getBalance().subtract(totalCost));

        if (sourceTeam != null) {
            sourceTeam.setBalance(sourceTeam.getBalance().add(transferValue));
        }

        player.setTeam(targetTeam);
        playerRepository.save(player);
    }

    private BigDecimal calculateTransferValue(Player player) {
        if (player.getAge() <= 0) {
            throw new BusinessException("too young");
        }

        double value = (double) player.getExperienceMonths() * 100000.0 / player.getAge();
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP);
    }

    private PlayerDTO convertToDTO(Player player) {
        PlayerDTO dto = new PlayerDTO();
        dto.setId(player.getId());
        dto.setName(player.getName());
        dto.setAge(player.getAge());
        dto.setExperienceMonths(player.getExperienceMonths());

        if (player.getTeam() != null) {
            dto.setTeam(new TeamSummaryDTO(player.getTeam().getId(), player.getTeam().getName()));
        }

        return dto;
    }

    private Player convertToEntity(PlayerDTO dto) {
        Player player = new Player();
        player.setName(dto.getName());
        player.setAge(dto.getAge());
        player.setExperienceMonths(dto.getExperienceMonths());
        return player;
    }
}

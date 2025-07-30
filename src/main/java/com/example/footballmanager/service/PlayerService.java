
package com.example.footballmanager.service;

import com.example.footballmanager.entity.Player;
import com.example.footballmanager.entity.Team;
import com.example.footballmanager.repository.PlayerRepository;
import com.example.footballmanager.repository.TeamRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    public PlayerService(PlayerRepository playerRepository, TeamRepository teamRepository) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
    }

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public Player getPlayerById(Long id) {
        return playerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Player not found"));
    }

    public Player createPlayer(Player player) {
        return playerRepository.save(player);
    }

    public Player updatePlayer(Long id, Player updated) {
        Player player = getPlayerById(id);
        player.setName(updated.getName());
        player.setAge(updated.getAge());
        player.setExperienceMonths(updated.getExperienceMonths());
        player.setTeam(updated.getTeam());
        return playerRepository.save(player);
    }

    public void deletePlayer(Long id) {
        playerRepository.deleteById(id);
    }

    @Transactional
    public void transferPlayer(Long playerId, Long targetTeamId) {
        Player player = getPlayerById(playerId);
        Team fromTeam = player.getTeam();
        Team toTeam = teamRepository.findById(targetTeamId)
                .orElseThrow(() -> new EntityNotFoundException("Target team not found"));

        if (player.getAge() <= 0)
            throw new IllegalArgumentException("Invalid player age");

        BigDecimal transferValue = BigDecimal.valueOf(player.getExperienceMonths() * 100000.0 / player.getAge());
        BigDecimal commission = transferValue.multiply(BigDecimal.valueOf(toTeam.getCommission()));
        BigDecimal totalCost = transferValue.add(commission);

        if (toTeam.getBalance().compareTo(totalCost) < 0) {
            throw new IllegalStateException("Not enough balance to perform transfer");
        }

        toTeam.setBalance(toTeam.getBalance().subtract(totalCost));
        if (fromTeam != null) {
            fromTeam.setBalance(fromTeam.getBalance().add(totalCost));
            teamRepository.save(fromTeam);
        }

        player.setTeam(toTeam);
        playerRepository.save(player);
        teamRepository.save(toTeam);
    }
}

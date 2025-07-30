
package com.example.footballmanager.controller;

import com.example.footballmanager.dto.PlayerTransferRequest;
import com.example.footballmanager.entity.Player;
import com.example.footballmanager.service.PlayerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/players")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public List<Player> getAll() {
        return playerService.getAllPlayers();
    }

    @GetMapping("/{id}")
    public Player getById(@PathVariable Long id) {
        return playerService.getPlayerById(id);
    }

    @PostMapping
    public Player create(@RequestBody @Valid Player player) {
        return playerService.createPlayer(player);
    }

    @PutMapping("/{id}")
    public Player update(@PathVariable Long id, @RequestBody @Valid Player player) {
        return playerService.updatePlayer(id, player);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        playerService.deletePlayer(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/transfer")
    public ResponseEntity<String> transfer(@PathVariable Long id, @RequestBody @Valid PlayerTransferRequest request) {
        playerService.transferPlayer(id, request.getTargetTeamId());
        return ResponseEntity.ok("Player transferred successfully");
    }
}

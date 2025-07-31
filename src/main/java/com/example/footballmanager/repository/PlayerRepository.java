package com.example.footballmanager.repository;

import com.example.footballmanager.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    @Query("SELECT p FROM Player p LEFT JOIN FETCH p.team")
    List<Player> findAllWithTeam();

    @Query("SELECT p FROM Player p LEFT JOIN FETCH p.team WHERE p.id = :id")
    Optional<Player> findByIdWithTeam(@Param("id") Long id);

    List<Player> findByTeamId(Long teamId);
}
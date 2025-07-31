package com.example.footballmanager.config;

import com.example.footballmanager.entity.Player;
import com.example.footballmanager.entity.Team;
import com.example.footballmanager.repository.PlayerRepository;
import com.example.footballmanager.repository.TeamRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(TeamRepository teamRepository, PlayerRepository playerRepository) {
        return args -> {
            if (teamRepository.count() > 0) {
                return;
            }

            Team realMadrid = new Team("Real Madrid", new BigDecimal("50000000"), 0.05);
            Team barcelona = new Team("FC Barcelona", new BigDecimal("45000000"), 0.06);
            Team manchesterUnited = new Team("Manchester United", new BigDecimal("40000000"), 0.07);
            Team bayernMunich = new Team("Bayern Munich", new BigDecimal("35000000"), 0.08);
            Team liverpool = new Team("Liverpool FC", new BigDecimal("30000000"), 0.09);

            teamRepository.save(realMadrid);
            teamRepository.save(barcelona);
            teamRepository.save(manchesterUnited);
            teamRepository.save(bayernMunich);
            teamRepository.save(liverpool);

            Player player1 = new Player("Karim Benzema", 35, 180);
            player1.setTeam(realMadrid);

            Player player2 = new Player("Luka Modric", 37, 200);
            player2.setTeam(realMadrid);

            Player player3 = new Player("Vinicius Junior", 23, 60);
            player3.setTeam(realMadrid);

            Player player4 = new Player("Robert Lewandowski", 34, 190);
            player4.setTeam(barcelona);

            Player player5 = new Player("Pedri", 20, 36);
            player5.setTeam(barcelona);

            Player player6 = new Player("Gavi", 19, 24);
            player6.setTeam(barcelona);

            Player player7 = new Player("Marcus Rashford", 26, 120);
            player7.setTeam(manchesterUnited);

            Player player8 = new Player("Bruno Fernandes", 29, 100);
            player8.setTeam(manchesterUnited);

            Player player9 = new Player("Thomas Muller", 34, 220);
            player9.setTeam(bayernMunich);

            Player player10 = new Player("Joshua Kimmich", 28, 144);
            player10.setTeam(bayernMunich);

            Player player11 = new Player("Mohamed Salah", 31, 84);
            player11.setTeam(liverpool);

            Player player12 = new Player("Sadio Mane", 31, 96);
            player12.setTeam(liverpool);

            Player freeAgent1 = new Player("Cristiano Ronaldo", 39, 250);
            Player freeAgent2 = new Player("Neymar Jr", 31, 156);

            playerRepository.save(player1);
            playerRepository.save(player2);
            playerRepository.save(player3);
            playerRepository.save(player4);
            playerRepository.save(player5);
            playerRepository.save(player6);
            playerRepository.save(player7);
            playerRepository.save(player8);
            playerRepository.save(player9);
            playerRepository.save(player10);
            playerRepository.save(player11);
            playerRepository.save(player12);
            playerRepository.save(freeAgent1);
            playerRepository.save(freeAgent2);

            System.out.println("data initialized");
            System.out.println("created teams: " + teamRepository.count());
            System.out.println("created players: " + playerRepository.count());
        };
    }
}

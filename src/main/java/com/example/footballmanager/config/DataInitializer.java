
package com.example.footballmanager.config;

import com.example.footballmanager.entity.Player;
import com.example.footballmanager.entity.Team;
import com.example.footballmanager.repository.PlayerRepository;
import com.example.footballmanager.repository.TeamRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.Arrays;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(TeamRepository teamRepository, PlayerRepository playerRepository) {
        return args -> {
            Team team1 = new Team();
            team1.setName("Real Madrid");
            team1.setBalance(new BigDecimal("10000000"));
            team1.setCommission(0.05);

            Team team2 = new Team();
            team2.setName("Manchester United");
            team2.setBalance(new BigDecimal("8000000"));
            team2.setCommission(0.07);

            teamRepository.saveAll(Arrays.asList(team1, team2));

            Player p1 = new Player();
            p1.setName("John Doe");
            p1.setAge(25);
            p1.setExperienceMonths(48);
            p1.setTeam(team1);

            Player p2 = new Player();
            p2.setName("Max Smith");
            p2.setAge(30);
            p2.setExperienceMonths(96);
            p2.setTeam(team2);

            playerRepository.saveAll(Arrays.asList(p1, p2));
        };
    }
}

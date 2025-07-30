
package com.example.footballmanager.entity;

import jakarta.persistence.*;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int age;

    private int experienceMonths;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    // Геттери і сеттери
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public int getExperienceMonths() { return experienceMonths; }
    public void setExperienceMonths(int experienceMonths) { this.experienceMonths = experienceMonths; }

    public Team getTeam() { return team; }
    public void setTeam(Team team) { this.team = team; }
}

package com.example.footballmanager.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 100)
    @Column(nullable = false)
    private String name;

    @NotNull
    @Min(value = 16)
    @Max(value = 50)
    @Column(nullable = false)
    private Integer age;

    @NotNull
    @Min(value = 0)
    @Column(nullable = false)
    private Integer experienceMonths;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    @JsonBackReference
    private Team team;

    public Player() {}

    public Player(String name, Integer age, Integer experienceMonths) {
        this.name = name;
        this.age = age;
        this.experienceMonths = experienceMonths;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public Integer getExperienceMonths() { return experienceMonths; }
    public void setExperienceMonths(Integer experienceMonths) { this.experienceMonths = experienceMonths; }

    public Team getTeam() { return team; }
    public void setTeam(Team team) { this.team = team; }
}
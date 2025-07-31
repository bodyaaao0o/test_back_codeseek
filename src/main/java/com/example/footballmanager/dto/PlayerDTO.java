package com.example.footballmanager.dto;

import jakarta.validation.constraints.*;

public class PlayerDTO {
    private Long id;

    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    @NotNull
    @Min(value = 16)
    @Max(value = 50)
    private Integer age;

    @NotNull
    @Min(value = 0)
    private Integer experienceMonths;

    private TeamSummaryDTO team;

    public PlayerDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public Integer getExperienceMonths() { return experienceMonths; }
    public void setExperienceMonths(Integer experienceMonths) { this.experienceMonths = experienceMonths; }

    public TeamSummaryDTO getTeam() { return team; }
    public void setTeam(TeamSummaryDTO team) { this.team = team; }
}

package com.example.footballmanager.dto;

public class PlayerSummaryDTO {
    private Long id;
    private String name;
    private Integer age;
    private Integer experienceMonths;

    public PlayerSummaryDTO() {}

    public PlayerSummaryDTO(Long id, String name, Integer age, Integer experienceMonths) {
        this.id = id;
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
}

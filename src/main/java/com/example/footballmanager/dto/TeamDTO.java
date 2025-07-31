package com.example.footballmanager.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

public class TeamDTO {
    private Long id;

    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    @NotNull
    @DecimalMin(value = "0.0")
    private BigDecimal balance;

    @NotNull
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "0.1")
    private Double commission;

    private List<PlayerSummaryDTO> players;

    public TeamDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }

    public Double getCommission() { return commission; }
    public void setCommission(Double commission) { this.commission = commission; }

    public List<PlayerSummaryDTO> getPlayers() { return players; }
    public void setPlayers(List<PlayerSummaryDTO> players) { this.players = players; }
}

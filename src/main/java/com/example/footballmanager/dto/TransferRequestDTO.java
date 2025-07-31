package com.example.footballmanager.dto;

import jakarta.validation.constraints.NotNull;

public class TransferRequestDTO {
    @NotNull
    private Long targetTeamId;

    public TransferRequestDTO() {}

    public TransferRequestDTO(Long targetTeamId) {
        this.targetTeamId = targetTeamId;
    }

    public Long getTargetTeamId() { return targetTeamId; }
    public void setTargetTeamId(Long targetTeamId) { this.targetTeamId = targetTeamId; }
}
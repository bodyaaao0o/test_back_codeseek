
package com.example.footballmanager.dto;

import jakarta.validation.constraints.NotNull;

public class PlayerTransferRequest {

    @NotNull
    private Long targetTeamId;

    public Long getTargetTeamId() {
        return targetTeamId;
    }

    public void setTargetTeamId(Long targetTeamId) {
        this.targetTeamId = targetTeamId;
    }
}

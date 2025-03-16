package com.michael_delivery.backend.dto;

import jakarta.validation.constraints.NotNull;


public class PasswordResetDTO {

    private Long passwordResetId;

    @NotNull
    private Long user;

    private Long reseatedBy;

    public Long getPasswordResetId() {
        return passwordResetId;
    }

    public void setPasswordResetId(final Long passwordResetId) {
        this.passwordResetId = passwordResetId;
    }

    public Long getUser() {
        return user;
    }

    public void setUser(final Long user) {
        this.user = user;
    }

    public Long getReseatedBy() {
        return reseatedBy;
    }

    public void setReseatedBy(final Long reseatedBy) {
        this.reseatedBy = reseatedBy;
    }

}

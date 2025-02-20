package com.michael_delivery.backend.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;


public class DeleteRequestDTO {

    private Long deleteRequestId;

    @NotBlank(message = "reason is required")
    private String reason;

    private String note;


    @NotNull
    private Long user;

    public Long getDeleteRequestId() {
        return deleteRequestId;
    }

    public void setDeleteRequestId(final Long deleteRequestId) {
        this.deleteRequestId = deleteRequestId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(final String reason) {
        this.reason = reason;
    }

    public String getNote() {
        return note;
    }

    public void setNote(final String note) {
        this.note = note;
    }

    public Long getUser() {
        return user;
    }

    public void setUser(final Long user) {
        this.user = user;
    }

}

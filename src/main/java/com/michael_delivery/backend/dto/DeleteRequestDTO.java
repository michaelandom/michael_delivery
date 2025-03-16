package com.michael_delivery.backend.dto;

import com.michael_delivery.backend.enums.ReasonType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;


public class DeleteRequestDTO {

    private Long deleteRequestId;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "reason is required")
    private ReasonType reason;

    private String note;


    @NotNull
    private Long user;

    public Long getDeleteRequestId() {
        return deleteRequestId;
    }

    public void setDeleteRequestId(final Long deleteRequestId) {
        this.deleteRequestId = deleteRequestId;
    }

    public ReasonType getReason() {
        return reason;
    }

    public void setReason(final ReasonType reason) {
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

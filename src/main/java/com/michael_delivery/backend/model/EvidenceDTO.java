package com.michael_delivery.backend.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;
import java.util.List;


public class EvidenceDTO {

    private Long evidenceId;

    private List<String> urls;

    private String recipientName;

    private OffsetDateTime recipientDob;

    private String note;

    private OffsetDateTime time;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    @NotBlank(message = "destination is required")
    private Long destination;

    public Long getEvidenceId() {
        return evidenceId;
    }

    public void setEvidenceId(final Long evidenceId) {
        this.evidenceId = evidenceId;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(final List<String> url) {
        this.urls.addAll(url);
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(final String recipientName) {
        this.recipientName = recipientName;
    }

    public OffsetDateTime getRecipientDob() {
        return recipientDob;
    }

    public void setRecipientDob(final OffsetDateTime recipientDob) {
        this.recipientDob = recipientDob;
    }

    public String getNote() {
        return note;
    }

    public void setNote(final String note) {
        this.note = note;
    }

    public OffsetDateTime getTime() {
        return time;
    }

    public void setTime(final OffsetDateTime time) {
        this.time = time;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(final OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getDestination() {
        return destination;
    }

    public void setDestination(final Long destination) {
        this.destination = destination;
    }

}

package com.michael_delivery.backend.model;

import jakarta.validation.constraints.*;

import java.time.OffsetDateTime;
import java.util.List;

import static com.michael_delivery.backend.util.ValidationConstants.URLs.URL_PATTERN;


public class EvidenceDTO {

    private Long evidenceId;

    @NotEmpty(message = "URLs list cannot be empty")
    private List<@Pattern(regexp = URL_PATTERN,
            message = "Invalid URL format") String> urls;

    @NotBlank(message = "Recipient name is required")
    @Size(min = 2, max = 100, message = "Recipient name must be between 2 and 100 characters")
    private String recipientName;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    private OffsetDateTime recipientDob;


    private String note;

    @NotNull(message = "Time is required")
    private OffsetDateTime time;

    @NotNull
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
        this.urls=url;
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

    public Long getDestination() {
        return destination;
    }

    public void setDestination(final Long destination) {
        this.destination = destination;
    }

}

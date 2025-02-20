package com.michael_delivery.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;


public class DriverGuideDTO {

    private Long driverGuideId;

    @NotBlank(message = "fileUrl is required")
    private String fileUrl;

    private String description;

    private Boolean isImportant;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    public Long getDriverGuideId() {
        return driverGuideId;
    }

    public void setDriverGuideId(final Long driverGuideId) {
        this.driverGuideId = driverGuideId;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(final String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Boolean getIsImportant() {
        return isImportant;
    }

    public void setIsImportant(final Boolean isImportant) {
        this.isImportant = isImportant;
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

}

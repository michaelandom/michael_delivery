package com.michael_delivery.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public class DriverGuideDTO {

    private Long driverGuideId;

    @NotBlank(message = "fileUrl is required")
    private String fileUrl;

    private String description;

    @NotNull
    private Boolean isImportant;

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

}

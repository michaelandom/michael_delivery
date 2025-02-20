package com.michael_delivery.backend.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;


public class AppVersionDTO {

    private Integer appVersionId;

    @NotBlank(message = "appName is required")
    @Size(min = 3, max = 255, message = "appName must be between 3 and 255 characters")
    private String appName;

    @NotNull
    private Boolean updateType;

    @NotBlank(message = "Version is required")
    @Size(min = 3, max = 255, message = "Version must be between 3 and 255 characters")
    private String version;

    public Integer getAppVersionId() {
        return appVersionId;
    }

    public void setAppVersionId(final Integer appVersionId) {
        this.appVersionId = appVersionId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(final String appName) {
        this.appName = appName;
    }

    public Boolean getUpdateType() {
        return updateType;
    }

    public void setUpdateType(final Boolean updateType) {
        this.updateType = updateType;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(final String version) {
        this.version = version;
    }

}

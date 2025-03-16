package com.michael_delivery.backend.dto;

import com.michael_delivery.backend.enums.AppNameType;
import com.michael_delivery.backend.interfaces.EnumValid;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class AppVersionDTO {

    private Integer appVersionId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @EnumValid(enumClass = AppNameType.class,message = "Invalid appName value")
    private AppNameType appName;

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

    public AppNameType getAppName() {
        return appName;
    }

    public void setAppName(final AppNameType appName) {
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

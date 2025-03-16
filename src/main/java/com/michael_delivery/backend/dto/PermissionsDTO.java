package com.michael_delivery.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public class PermissionsDTO {

    private Long permissionId;

    @NotBlank(message = "permissionName is required")
    private String permissionName;

    @NotNull
    private String description;


    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(final Long permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(final String permissionName) {
        this.permissionName = permissionName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }


}

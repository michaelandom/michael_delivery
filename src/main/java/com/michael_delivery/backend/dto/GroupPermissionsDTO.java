package com.michael_delivery.backend.dto;

import jakarta.validation.constraints.NotNull;


public class GroupPermissionsDTO {

    private Long groupPermissionId;

    @NotNull
    private Long group;

    @NotNull
    private Long permission;

    public Long getGroupPermissionId() {
        return groupPermissionId;
    }

    public void setGroupPermissionId(final Long groupPermissionId) {
        this.groupPermissionId = groupPermissionId;
    }

    public Long getGroup() {
        return group;
    }

    public void setGroup(final Long group) {
        this.group = group;
    }

    public Long getPermission() {
        return permission;
    }

    public void setPermission(final Long permission) {
        this.permission = permission;
    }

}

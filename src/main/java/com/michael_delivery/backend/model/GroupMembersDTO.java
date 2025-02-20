package com.michael_delivery.backend.model;

import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;


public class GroupMembersDTO {

    private Long groupMemberId;

    @NotNull
    private Long group;

    @NotNull
    private Long user;

    public Long getGroupMemberId() {
        return groupMemberId;
    }

    public void setGroupMemberId(final Long groupMemberId) {
        this.groupMemberId = groupMemberId;
    }

    public Long getGroup() {
        return group;
    }

    public void setGroup(final Long group) {
        this.group = group;
    }

    public Long getUser() {
        return user;
    }

    public void setUser(final Long user) {
        this.user = user;
    }

}

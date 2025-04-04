package com.michael_delivery.backend.dto;

import com.michael_delivery.backend.enums.GroupType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public class GroupsDTO {

    private Long groupId;

    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "description is required")
    private String description;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "groupType is required")
    private GroupType groupType;

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(final Long groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public GroupType getGroupType() {
        return groupType;
    }

    public void setGroupType(final GroupType groupType) {
        this.groupType = groupType;
    }

}

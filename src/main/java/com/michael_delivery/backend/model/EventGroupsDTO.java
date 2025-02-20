package com.michael_delivery.backend.model;

import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;


public class EventGroupsDTO {

    private Long eventGroupId;

    @NotNull
    private Long group;

    @NotNull
    private Long event;

    public Long getEventGroupId() {
        return eventGroupId;
    }

    public void setEventGroupId(final Long eventGroupId) {
        this.eventGroupId = eventGroupId;
    }

    public Long getGroup() {
        return group;
    }

    public void setGroup(final Long group) {
        this.group = group;
    }

    public Long getEvent() {
        return event;
    }

    public void setEvent(final Long event) {
        this.event = event;
    }

}

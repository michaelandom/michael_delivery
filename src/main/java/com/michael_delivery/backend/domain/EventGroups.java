package com.michael_delivery.backend.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;


@Entity
@Table(name = "EventGroups")
@EntityListeners(AuditingEntityListener.class)
public class EventGroups {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventGroupId;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private Groups group;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Events event;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    public Long getEventGroupId() {
        return eventGroupId;
    }

    public void setEventGroupId(final Long eventGroupId) {
        this.eventGroupId = eventGroupId;
    }


    public Groups getGroup() {
        return group;
    }

    public void setGroup(final Groups group) {
        this.group = group;
    }

    public Events getEvent() {
        return event;
    }

    public void setEvent(final Events event) {
        this.event = event;
    }


}

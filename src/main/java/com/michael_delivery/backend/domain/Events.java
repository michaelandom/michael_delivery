package com.michael_delivery.backend.domain;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;
import java.util.Set;


@Entity
@Table(name = "Events")
@EntityListeners(AuditingEntityListener.class)
public class Events {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;

    @Column(nullable = false, columnDefinition = "longtext")
    private String title;

    @Column(columnDefinition = "longtext")
    private String link;

    @Column(columnDefinition = "longtext")
    private String contents;

    @Column(nullable = false)
    private OffsetDateTime startDate;

    @Column
    private OffsetDateTime endDate;

    @Column(nullable = false, columnDefinition = "tinyint", length = 1)
    private Boolean sendPushNotification;

    @Column(nullable = false, columnDefinition = "longtext")
    private String bannerImageUrl;

    

    @OneToMany(mappedBy = "event")
    private Set<EventGroups> eventEventGroups;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(final Long eventId) {
        this.eventId = eventId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(final String link) {
        this.link = link;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(final String contents) {
        this.contents = contents;
    }

    public OffsetDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(final OffsetDateTime startDate) {
        this.startDate = startDate;
    }

    public OffsetDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(final OffsetDateTime endDate) {
        this.endDate = endDate;
    }

    public Boolean getSendPushNotification() {
        return sendPushNotification;
    }

    public void setSendPushNotification(final Boolean sendPushNotification) {
        this.sendPushNotification = sendPushNotification;
    }

    public String getBannerImageUrl() {
        return bannerImageUrl;
    }

    public void setBannerImageUrl(final String bannerImageUrl) {
        this.bannerImageUrl = bannerImageUrl;
    }


    public Set<EventGroups> getEventEventGroups() {
        return eventEventGroups;
    }

    public void setEventEventGroups(final Set<EventGroups> eventEventGroups) {
        this.eventEventGroups = eventEventGroups;
    }


}

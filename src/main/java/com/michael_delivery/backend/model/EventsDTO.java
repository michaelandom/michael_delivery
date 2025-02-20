package com.michael_delivery.backend.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;


public class EventsDTO {

    private Long eventId;

    @NotBlank(message = "title is required")
    private String title;

    private String link;

    private String contents;
    @NotBlank(message = "startDate is required")
    private OffsetDateTime startDate;

    private OffsetDateTime endDate;

    @NotBlank(message = "sendPushNotification is required")
    private Boolean sendPushNotification;

    @NotBlank(message = "bannerImage is required")
    private String bannerImage;


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

    public String getBannerImage() {
        return bannerImage;
    }

    public void setBannerImageUrl(final String bannerImage) {
        this.bannerImage = bannerImage;
    }

}

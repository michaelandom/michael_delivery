package com.michael_delivery.backend.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;

import static com.michael_delivery.backend.util.ValidationConstants.URLs.URL_PATTERN;


public class EventsDTO {

    private Long eventId;

    @NotBlank(message = "title is required")
    private String title;

    private String link;

    private String contents;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime startDate;

    private OffsetDateTime endDate;

    @NotNull(message = "sendPushNotification is required")
    private Boolean sendPushNotification;

    @NotBlank(message = "bannerImageUrl is required")
    @Pattern(regexp = URL_PATTERN, message = "Invalid URL format")
    private String bannerImageUrl;


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

}

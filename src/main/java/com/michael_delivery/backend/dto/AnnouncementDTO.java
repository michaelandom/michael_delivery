package com.michael_delivery.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

import static com.michael_delivery.backend.util.ValidationConstants.URLs.URL_PATTERN;


public class AnnouncementDTO {
    private Long announcementId;

    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 255, message = "Title must be between 3 and 255 characters")
    private String title;

    @NotBlank(message = "Content is required")
    @NotNull
    private String content;

    @URL(message = "Must be a valid URL")
    @Pattern(regexp = URL_PATTERN, message = "Invalid URL format")
    @Schema(example = "https://profilePicture.png")
    private String imageUrl;

    public Long getAnnouncementId() {
        return announcementId;
    }

    public void setAnnouncementId(final Long announcementId) {
        this.announcementId = announcementId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

}

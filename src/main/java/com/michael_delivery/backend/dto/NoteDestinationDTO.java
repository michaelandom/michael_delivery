package com.michael_delivery.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.List;

import static com.michael_delivery.backend.util.ValidationConstants.URLs.URL_PATTERN;


public class NoteDestinationDTO {

    private Long noteDestinationId;

    @NotBlank(message = "note is required")
    private String note;

    private List<@Pattern(regexp = URL_PATTERN, message = "Invalid URL format") String>  photoUrls;

    @NotNull(message = "destination is required")
    private Long destination;

    public Long getNoteDestinationId() {
        return noteDestinationId;
    }

    public void setNoteDestinationId(final Long noteDestinationId) {
        this.noteDestinationId = noteDestinationId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(final String note) {
        this.note = note;
    }

    public List<String> getPhotoUrls() {
        return photoUrls;
    }

    public void setPhotoUrls(final List<String> photoUrls) {
        this.photoUrls=photoUrls;
    }
    public Long getDestination() {
        return destination;
    }

    public void setDestination(final Long destination) {
        this.destination = destination;
    }

}

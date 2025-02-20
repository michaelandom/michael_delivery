package com.michael_delivery.backend.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;
import java.util.List;


public class NoteDestinationDTO {

    private Long noteDestinationId;

    @NotBlank(message = "note is required")
    private String note;

    private List<String>  photoUrls;
    @NotBlank(message = "destination is required")
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
        this.photoUrls.addAll(photoUrls);
    }
    public Long getDestination() {
        return destination;
    }

    public void setDestination(final Long destination) {
        this.destination = destination;
    }

}

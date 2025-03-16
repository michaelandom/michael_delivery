package com.michael_delivery.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.List;

import static com.michael_delivery.backend.util.ValidationConstants.URLs.URL_PATTERN;


public class NoteDeliveryDetailDTO {

    private Long noteDeliveryDetailId;

    @NotBlank(message = "destination is required")
    private String note;

    private List<@Pattern(regexp = URL_PATTERN, message = "Invalid URL format") String> photoUrls;

    @NotNull
    private Long deliveryDetail;

    public Long getNoteDeliveryDetailId() {
        return noteDeliveryDetailId;
    }

    public void setNoteDeliveryDetailId(final Long noteDeliveryDetailId) {
        this.noteDeliveryDetailId = noteDeliveryDetailId;
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

   public Long getDeliveryDetail() {
        return deliveryDetail;
    }

    public void setDeliveryDetail(final Long deliveryDetail) {
        this.deliveryDetail = deliveryDetail;
    }

}

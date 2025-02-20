package com.michael_delivery.backend.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;
import java.util.List;


public class NoteDeliveryDetailDTO {

    private Long noteDeliveryDetailId;

    @NotBlank(message = "destination is required")
    private String note;

    private List<String> photoUrls;

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
        this.photoUrls.addAll(photoUrls);
    }

   public Long getDeliveryDetail() {
        return deliveryDetail;
    }

    public void setDeliveryDetail(final Long deliveryDetail) {
        this.deliveryDetail = deliveryDetail;
    }

}

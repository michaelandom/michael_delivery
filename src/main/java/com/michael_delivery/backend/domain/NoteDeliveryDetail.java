package com.michael_delivery.backend.domain;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;
import java.util.List;


@Entity
@Table(name = "NoteDeliveryDetails")
@EntityListeners(AuditingEntityListener.class)
public class NoteDeliveryDetail {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noteDeliveryDetailId;

    @Column(nullable = false, columnDefinition = "longtext")
    private String note;

    @Lob
    @Column(nullable = false, columnDefinition = "json")
    @Type(JsonType.class)
    private List<String> photoUrls;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_detail_id", nullable = false)
    private DeliveryDetail deliveryDetail;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime updatedAt;

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

    public List<String> getPhotoUrls() {return photoUrls;}

    public void setPhotoUrls(final String photoUrl) {
        this.photoUrls.add(photoUrl);
    }


    public DeliveryDetail getDeliveryDetail() {
        return deliveryDetail;
    }

    public void setDeliveryDetail(final DeliveryDetail deliveryDetail) {
        this.deliveryDetail = deliveryDetail;
    }


}

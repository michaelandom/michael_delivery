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
@Table(name = "NoteDestinations")
@EntityListeners(AuditingEntityListener.class)
public class NoteDestination {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noteDestinationId;

    @Column(nullable = false, columnDefinition = "longtext")
    private String note;

    @Lob
    @Column(nullable = false, columnDefinition = "json")
    @Type(JsonType.class)
    private List<String> photoUrls;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_id", nullable = false)
    private Destination destination;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime updatedAt;

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

    public void setPhotoUrls(final String photos) {
        this.photoUrls.add(photos);
    }


    public Destination getDestination() {
        return destination;
    }

    public void setDestination(final Destination destination) {
        this.destination = destination;
    }


}

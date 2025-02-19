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
@Table(name = "Evidences")
@EntityListeners(AuditingEntityListener.class)
public class Evidence {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long evidenceId;

    @Lob
    @Column(nullable = false,columnDefinition = "JSON")
    @Type(JsonType.class)
    private List<String> urls;

    @Column
    private String recipientName;

    @Column
    private OffsetDateTime recipientDob;

    @Column(columnDefinition = "longtext")
    private String note;

    @Column
    private OffsetDateTime time;

    

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_id", nullable = false)
    private Destination destination;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    public Long getEvidenceId() {
        return evidenceId;
    }

    public void setEvidenceId(final Long evidenceId) {
        this.evidenceId = evidenceId;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(final String url) {
        this.urls.add(url);
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(final String recipientName) {
        this.recipientName = recipientName;
    }

    public OffsetDateTime getRecipientDob() {
        return recipientDob;
    }

    public void setRecipientDob(final OffsetDateTime recipientDob) {
        this.recipientDob = recipientDob;
    }

    public String getNote() {
        return note;
    }

    public void setNote(final String note) {
        this.note = note;
    }

    public OffsetDateTime getTime() {
        return time;
    }

    public void setTime(final OffsetDateTime time) {
        this.time = time;
    }


    public Destination getDestination() {
        return destination;
    }

    public void setDestination(final Destination destination) {
        this.destination = destination;
    }


}

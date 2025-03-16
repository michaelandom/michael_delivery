package com.michael_delivery.backend.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;


@Entity
@Table(name = "DriverGuides")
@EntityListeners(AuditingEntityListener.class)
public class DriverGuide extends BaseModel<Long> {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long driverGuideId;

    @Column(nullable = false, columnDefinition = "longtext")
    private String fileUrl;

    @Column(columnDefinition = "longtext")
    private String description;

    @Column(columnDefinition = "tinyint", length = 1)
    private Boolean isImportant;


    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    public Long getDriverGuideId() {
        return driverGuideId;
    }

    public void setDriverGuideId(final Long driverGuideId) {
        this.driverGuideId = driverGuideId;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(final String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Boolean getIsImportant() {
        return isImportant;
    }

    public void setIsImportant(final Boolean isImportant) {
        this.isImportant = isImportant;
    }

    @Override
    public Long getId() {
        return driverGuideId;
    }

}

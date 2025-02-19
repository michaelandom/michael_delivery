package com.michael_delivery.backend.domain;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;


@Entity
@Table(name = "AppVersions")
@EntityListeners(AuditingEntityListener.class)
public class AppVersion {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer appVersionId;

    @Column(nullable = false)
    private String appName;

    @Column(nullable = false, columnDefinition = "tinyint", length = 1)
    private Boolean updateType;

    @Column(nullable = false, length = 50)
    private String version;
    

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    public Integer getAppVersionId() {
        return appVersionId;
    }

    public void setAppVersionId(final Integer appVersionId) {
        this.appVersionId = appVersionId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(final String appName) {
        this.appName = appName;
    }

    public Boolean getUpdateType() {
        return updateType;
    }

    public void setUpdateType(final Boolean updateType) {
        this.updateType = updateType;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(final String version) {
        this.version = version;
    }



}

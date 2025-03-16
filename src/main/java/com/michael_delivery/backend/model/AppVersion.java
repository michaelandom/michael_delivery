package com.michael_delivery.backend.model;

import com.michael_delivery.backend.enums.AppNameType;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;


@Entity
@Table(name = "AppVersions")
@EntityListeners(AuditingEntityListener.class)
public class AppVersion extends BaseModel<Integer>{

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer appVersionId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppNameType appName;

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

    public AppNameType getAppName() {
        return appName;
    }

    public void setAppName(final AppNameType appName) {
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


    @Override
    public Integer getId() {
        return appVersionId;
    }
}

package com.michael_delivery.backend.domain;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;


@Entity
@Table(name = "ServiceAreas")
@EntityListeners(AuditingEntityListener.class)
public class ServiceArea {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serviceAreaId;

    @Column(nullable = false)
    private String name;

    @Column
    private String code;

    @Column(columnDefinition = "tinyint", length = 1)
    private Boolean isActive;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_name_id", nullable = false)
    private State stateName;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    public Long getServiceAreaId() {
        return serviceAreaId;
    }

    public void setServiceAreaId(final Long serviceAreaId) {
        this.serviceAreaId = serviceAreaId;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(final Boolean isActive) {
        this.isActive = isActive;
    }


    public State getStateName() {
        return stateName;
    }

    public void setStateName(final State stateName) {
        this.stateName = stateName;
    }


}

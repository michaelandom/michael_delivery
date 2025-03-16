package com.michael_delivery.backend.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;
import java.util.Set;


@Entity
@Table(name = "States")
@EntityListeners(AuditingEntityListener.class)
public class State extends BaseModel<Long>{

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stateId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 10)
    private String code;

    @Column
    private String logoUrl;


    @OneToMany(mappedBy = "state")
    private Set<ServiceArea> stateNameServiceAreas;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(final Long stateId) {
        this.stateId = stateId;
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

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(final String logoUrl) {
        this.logoUrl = logoUrl;
    }


    public Set<ServiceArea> getStateNameServiceAreas() {
        return stateNameServiceAreas;
    }

    public void setStateNameServiceAreas(final Set<ServiceArea> stateNameServiceAreas) {
        this.stateNameServiceAreas = stateNameServiceAreas;
    }

    @Override
    public Long getId() {
        return stateId;
    }

}

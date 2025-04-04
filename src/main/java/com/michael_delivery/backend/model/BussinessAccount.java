package com.michael_delivery.backend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;
import java.util.Set;


@Entity
@Table(name = "BussinessAccounts")
@EntityListeners(AuditingEntityListener.class)
public class BussinessAccount extends BaseModel<Long>{

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bussinessAccountId;

    @Column(nullable = false, length = 20)
    private String companyAbn;

    @Column(nullable = false)
    private String companyName;

    @Column(columnDefinition = "longtext")
    private String logoUrl;

    @Column(nullable = false, columnDefinition = "tinyint", length = 1)
    private Boolean isActive;

    @JsonManagedReference
    @OneToMany(mappedBy = "bussinessAccount")
    private Set<Users> bussinessAccountUsers;

    public Set<Users> getBussinessAccountUsers() {
        return bussinessAccountUsers;
    }

    public void setBussinessAccountUsers(Set<Users> bussinessAccountUsers) {
        this.bussinessAccountUsers = bussinessAccountUsers;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    public Long getBussinessAccountId() {
        return bussinessAccountId;
    }

    public void setBussinessAccountId(final Long bussinessAccountId) {
        this.bussinessAccountId = bussinessAccountId;
    }

    public String getCompanyAbn() {
        return companyAbn;
    }

    public void setCompanyAbn(final String companyAbn) {
        this.companyAbn = companyAbn;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(final String companyName) {
        this.companyName = companyName;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(final String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(final Boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public Long getId() {
        return bussinessAccountId;
    }
}

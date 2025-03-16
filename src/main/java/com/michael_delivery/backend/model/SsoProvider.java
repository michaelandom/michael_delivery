package com.michael_delivery.backend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;
import java.util.Set;


@Entity
@Table(name = "SsoProviders")
@EntityListeners(AuditingEntityListener.class)
public class SsoProvider extends BaseModel<Long> {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ssoProviderId;

    @Column(nullable = false)
    private String ssoProvider;

    @JsonManagedReference
    @OneToMany(mappedBy = "ssoProvider")
    private Set<Users> ssoProviderUsers;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    public Long getSsoProviderId() {
        return ssoProviderId;
    }

    public void setSsoProviderId(final Long ssoProviderId) {
        this.ssoProviderId = ssoProviderId;
    }

    public String getSsoProvider() {
        return ssoProvider;
    }

    public void setSsoProvider(final String ssoProvider) {
        this.ssoProvider = ssoProvider;
    }

    public Set<Users> getSsoProviderUsers() {
        return ssoProviderUsers;
    }

    public void setSsoProviderUsers(final Set<Users> ssoProviderUsers) {
        this.ssoProviderUsers = ssoProviderUsers;
    }

    @Override
    public Long getId() {
        return ssoProviderId;
    }
}

package com.michael_delivery.backend.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;
import java.util.Set;


@Entity
@Table(name = "Permissions")
@EntityListeners(AuditingEntityListener.class)
public class Permissions extends BaseModel<Long>{

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long permissionId;

    @Column(nullable = false)
    private String permissionName;

    @Column(
            nullable = false,
            columnDefinition = "longtext"
    )
    private String description;

    @JsonBackReference
    @OneToMany(mappedBy = "permission")
    private Set<GroupPermissions> permissionGroupPermissions;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(final Long permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(final String permissionName) {
        this.permissionName = permissionName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }


    public Set<GroupPermissions> getPermissionGroupPermissions() {
        return permissionGroupPermissions;
    }

    public void setPermissionGroupPermissions(
            final Set<GroupPermissions> permissionGroupPermissions) {
        this.permissionGroupPermissions = permissionGroupPermissions;
    }

    @Override
    public Long getId() {
        return permissionId;
    }

}

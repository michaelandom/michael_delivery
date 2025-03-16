package com.michael_delivery.backend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.michael_delivery.backend.enums.GroupType;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;
import java.util.Set;


@Entity
@Table(name = "MaGroups")
@EntityListeners(AuditingEntityListener.class)
public class Groups extends BaseModel<Long> {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupId;

    @Column(nullable = false)
    private String name;

    @Column(
            nullable = false,
            columnDefinition = "longtext"
    )
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GroupType groupType;


    @JsonManagedReference
    @OneToMany(mappedBy = "group")
    private Set<GroupMembers> groupGroupMembers;

    @JsonManagedReference
    @OneToMany(mappedBy = "group")
    private Set<GroupPermissions> groupGroupPermissions;

    @JsonManagedReference
    @OneToMany(mappedBy = "group")
    private Set<EventGroups> groupEventGroups;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(final Long groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public GroupType getGroupType() {
        return groupType;
    }

    public void setGroupType(final GroupType groupType) {
        this.groupType = groupType;
    }


    public Set<GroupMembers> getGroupGroupMembers() {
        return groupGroupMembers;
    }

    public void setGroupGroupMembers(final Set<GroupMembers> groupGroupMembers) {
        this.groupGroupMembers = groupGroupMembers;
    }

    public Set<GroupPermissions> getGroupGroupPermissions() {
        return groupGroupPermissions;
    }

    public void setGroupGroupPermissions(final Set<GroupPermissions> groupGroupPermissions) {
        this.groupGroupPermissions = groupGroupPermissions;
    }

    public Set<EventGroups> getGroupEventGroups() {
        return groupEventGroups;
    }

    public void setGroupEventGroups(final Set<EventGroups> groupEventGroups) {
        this.groupEventGroups = groupEventGroups;
    }

    @Override
    public Long getId() {
        return groupId;
    }

}

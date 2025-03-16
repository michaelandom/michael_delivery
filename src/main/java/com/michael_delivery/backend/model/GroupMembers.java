package com.michael_delivery.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;


@Entity
@Table(name = "GroupMembers")
@EntityListeners(AuditingEntityListener.class)
public class GroupMembers extends BaseModel<Long>{

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupMemberId;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private Groups group;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    public Long getGroupMemberId() {
        return groupMemberId;
    }

    public void setGroupMemberId(final Long groupMemberId) {
        this.groupMemberId = groupMemberId;
    }


    public Groups getGroup() {
        return group;
    }

    public void setGroup(final Groups group) {
        this.group = group;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(final Users user) {
        this.user = user;
    }

    @Override
    public Long getId() {
        return groupMemberId;
    }

}

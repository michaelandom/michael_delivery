package com.michael_delivery.backend.domain;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;


@Entity
@Table(name = "UserCoupons")
@EntityListeners(AuditingEntityListener.class)
public class UserCoupon {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userCouponId;

    

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id", nullable = false)
    private Coupons coupon;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    public Long getUserCouponId() {
        return userCouponId;
    }

    public void setUserCouponId(final Long userCouponId) {
        this.userCouponId = userCouponId;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(final Users user) {
        this.user = user;
    }

    public Coupons getCoupon() {
        return coupon;
    }

    public void setCoupon(final Coupons coupon) {
        this.coupon = coupon;
    }


}

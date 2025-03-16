package com.michael_delivery.backend.dto;

import jakarta.validation.constraints.NotNull;


public class UserCouponDTO {

    private Long userCouponId;

    @NotNull
    private Long user;

    @NotNull
    private Long coupon;

    public Long getUserCouponId() {
        return userCouponId;
    }

    public void setUserCouponId(final Long userCouponId) {
        this.userCouponId = userCouponId;
    }

    public Long getUser() {
        return user;
    }

    public void setUser(final Long user) {
        this.user = user;
    }

    public Long getCoupon() {
        return coupon;
    }

    public void setCoupon(final Long coupon) {
        this.coupon = coupon;
    }

}

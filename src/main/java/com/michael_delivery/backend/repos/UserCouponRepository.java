package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.Coupons;
import com.michael_delivery.backend.domain.UserCoupon;
import com.michael_delivery.backend.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {

    UserCoupon findFirstByUser(Users users);

    UserCoupon findFirstByCoupon(Coupons coupons);

}

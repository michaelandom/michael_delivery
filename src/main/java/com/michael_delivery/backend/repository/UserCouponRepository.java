package com.michael_delivery.backend.repository;

import com.michael_delivery.backend.model.Coupons;
import com.michael_delivery.backend.model.UserCoupon;
import com.michael_delivery.backend.model.Users;
import com.michael_delivery.backend.dto.UserCouponDTO;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserCouponRepository extends JpaRepository<UserCoupon, Long>  ,BaseRepository<UserCouponDTO,UserCoupon>{

    UserCoupon findFirstByUser(Users users);

    UserCoupon findFirstByCoupon(Coupons coupons);

}

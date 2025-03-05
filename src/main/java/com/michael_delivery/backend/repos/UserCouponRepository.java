package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.Coupons;
import com.michael_delivery.backend.domain.UserCoupon;
import com.michael_delivery.backend.domain.Users;
import com.michael_delivery.backend.model.UserCouponDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserCouponRepository extends JpaRepository<UserCoupon, Long>  ,BaseRepository<UserCouponDTO,UserCoupon>{

    UserCoupon findFirstByUser(Users users);

    UserCoupon findFirstByCoupon(Coupons coupons);

}

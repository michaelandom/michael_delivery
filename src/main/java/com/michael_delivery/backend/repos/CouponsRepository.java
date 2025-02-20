package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.Coupons;
import com.michael_delivery.backend.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CouponsRepository extends JpaRepository<Coupons, Long> {

    Coupons findFirstByCreatedBy(Users users);

}

package com.michael_delivery.backend.repository;

import com.michael_delivery.backend.model.Coupons;
import com.michael_delivery.backend.model.Users;
import com.michael_delivery.backend.dto.CouponsDTO;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CouponsRepository extends JpaRepository<Coupons, Long>,BaseRepository<CouponsDTO,Coupons> {

    Coupons findFirstByCreatedBy(Users users);

}

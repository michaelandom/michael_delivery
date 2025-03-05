package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.Coupons;
import com.michael_delivery.backend.domain.Users;
import com.michael_delivery.backend.model.CouponsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CouponsRepository extends JpaRepository<Coupons, Long> {

    Coupons findFirstByCreatedBy(Users users);

    public Page<CouponsDTO> findAll(Specification<Coupons> spec, Pageable pageable);
}

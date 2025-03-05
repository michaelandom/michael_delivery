package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.RiderCommission;
import com.michael_delivery.backend.model.RiderCommissionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RiderCommissionRepository extends JpaRepository<RiderCommission, Long> {

    RiderCommission findFirstByPreviousAndRiderCommissionIdNot(RiderCommission riderCommission,
            final Long riderCommissionId);

    public Page<RiderCommissionDTO> findAll(Specification<RiderCommission> spec, Pageable pageable);

}

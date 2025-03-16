package com.michael_delivery.backend.repository;

import com.michael_delivery.backend.model.RiderCommission;
import com.michael_delivery.backend.dto.RiderCommissionDTO;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RiderCommissionRepository extends JpaRepository<RiderCommission, Long>  ,BaseRepository<RiderCommissionDTO, RiderCommission> {

    RiderCommission findFirstByPreviousAndRiderCommissionIdNot(RiderCommission riderCommission,
            final Long riderCommissionId);


}

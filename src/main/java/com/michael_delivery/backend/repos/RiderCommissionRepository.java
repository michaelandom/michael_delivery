package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.RiderCommission;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RiderCommissionRepository extends JpaRepository<RiderCommission, Long> {

    RiderCommission findFirstByPreviousAndRiderCommissionIdNot(RiderCommission riderCommission,
            final Long riderCommissionId);


}

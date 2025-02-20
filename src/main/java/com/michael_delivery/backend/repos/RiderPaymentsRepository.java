package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.RiderPayments;
import com.michael_delivery.backend.domain.Riders;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RiderPaymentsRepository extends JpaRepository<RiderPayments, Long> {

    RiderPayments findFirstByRider(Riders riders);

}

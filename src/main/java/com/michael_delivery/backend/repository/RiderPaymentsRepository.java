package com.michael_delivery.backend.repository;

import com.michael_delivery.backend.model.RiderPayments;
import com.michael_delivery.backend.model.Riders;
import com.michael_delivery.backend.dto.RiderPaymentsDTO;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RiderPaymentsRepository extends JpaRepository<RiderPayments, Long>  ,BaseRepository<RiderPaymentsDTO, RiderPayments> {

    RiderPayments findFirstByRider(Riders riders);


}

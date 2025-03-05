package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.RiderPayments;
import com.michael_delivery.backend.domain.Riders;
import com.michael_delivery.backend.model.RiderPaymentsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RiderPaymentsRepository extends JpaRepository<RiderPayments, Long>  ,BaseRepository<RiderPaymentsDTO, RiderPayments> {

    RiderPayments findFirstByRider(Riders riders);


}

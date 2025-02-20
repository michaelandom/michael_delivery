package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.ExtrFee;
import com.michael_delivery.backend.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ExtrFeeRepository extends JpaRepository<ExtrFee, Long> {

    ExtrFee findFirstByOrder(Orders orders);

}

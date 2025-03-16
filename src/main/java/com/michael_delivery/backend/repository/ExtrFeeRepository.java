package com.michael_delivery.backend.repository;

import com.michael_delivery.backend.model.ExtrFee;
import com.michael_delivery.backend.model.Orders;
import com.michael_delivery.backend.dto.ExtrFeeDTO;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ExtrFeeRepository extends JpaRepository<ExtrFee, Long>,BaseRepository<ExtrFeeDTO,ExtrFee> {

    ExtrFee findFirstByOrder(Orders orders);
}

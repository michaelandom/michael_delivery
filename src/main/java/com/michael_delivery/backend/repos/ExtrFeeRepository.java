package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.ExtrFee;
import com.michael_delivery.backend.domain.Orders;
import com.michael_delivery.backend.model.ExtrFeeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ExtrFeeRepository extends JpaRepository<ExtrFee, Long>,BaseRepository<ExtrFeeDTO,ExtrFee> {

    ExtrFee findFirstByOrder(Orders orders);
}

package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.DeliveryDetail;
import com.michael_delivery.backend.domain.Orders;
import com.michael_delivery.backend.model.DeliveryDetailDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DeliveryDetailRepository extends JpaRepository<DeliveryDetail, Long> {

    DeliveryDetail findFirstByOrder(Orders orders);
    public Page<DeliveryDetailDTO> findAll(Specification<DeliveryDetail> spec, Pageable pageable);
}

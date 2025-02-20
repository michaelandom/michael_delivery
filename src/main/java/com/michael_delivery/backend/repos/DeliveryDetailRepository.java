package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.DeliveryDetail;
import com.michael_delivery.backend.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DeliveryDetailRepository extends JpaRepository<DeliveryDetail, Long> {

    DeliveryDetail findFirstByOrder(Orders orders);

}

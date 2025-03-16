package com.michael_delivery.backend.repository;

import com.michael_delivery.backend.model.DeliveryDetail;
import com.michael_delivery.backend.model.Orders;
import com.michael_delivery.backend.dto.DeliveryDetailDTO;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DeliveryDetailRepository extends JpaRepository<DeliveryDetail, Long>,BaseRepository<DeliveryDetailDTO,DeliveryDetail> {

    DeliveryDetail findFirstByOrder(Orders orders);
}

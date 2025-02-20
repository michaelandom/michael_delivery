package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.DeliveryDetail;
import com.michael_delivery.backend.domain.NoteDeliveryDetail;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NoteDeliveryDetailRepository extends JpaRepository<NoteDeliveryDetail, Long> {

    NoteDeliveryDetail findFirstByDeliveryDetail(DeliveryDetail deliveryDetail);

}

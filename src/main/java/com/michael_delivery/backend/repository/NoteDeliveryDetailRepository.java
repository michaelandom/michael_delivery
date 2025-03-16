package com.michael_delivery.backend.repository;

import com.michael_delivery.backend.model.DeliveryDetail;
import com.michael_delivery.backend.model.NoteDeliveryDetail;
import com.michael_delivery.backend.dto.NoteDeliveryDetailDTO;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NoteDeliveryDetailRepository extends JpaRepository<NoteDeliveryDetail, Long> ,BaseRepository<NoteDeliveryDetailDTO,NoteDeliveryDetail> {

    NoteDeliveryDetail findFirstByDeliveryDetail(DeliveryDetail deliveryDetail);


}

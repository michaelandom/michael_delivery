package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.DeliveryDetail;
import com.michael_delivery.backend.domain.NoteDeliveryDetail;
import com.michael_delivery.backend.model.NoteDeliveryDetailDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NoteDeliveryDetailRepository extends JpaRepository<NoteDeliveryDetail, Long> ,BaseRepository<NoteDeliveryDetailDTO,NoteDeliveryDetail> {

    NoteDeliveryDetail findFirstByDeliveryDetail(DeliveryDetail deliveryDetail);


}

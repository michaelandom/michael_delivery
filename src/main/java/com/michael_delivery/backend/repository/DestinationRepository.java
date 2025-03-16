package com.michael_delivery.backend.repository;

import com.michael_delivery.backend.model.Destination;
import com.michael_delivery.backend.model.Orders;
import com.michael_delivery.backend.model.Riders;
import com.michael_delivery.backend.dto.DestinationDTO;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DestinationRepository extends JpaRepository<Destination, Long>,BaseRepository<DestinationDTO,Destination> {

    Destination findFirstByOrder(Orders orders);

    Destination findFirstByDeliveryBy(Riders riders);

}

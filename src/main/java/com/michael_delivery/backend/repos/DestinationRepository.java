package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.Destination;
import com.michael_delivery.backend.domain.Orders;
import com.michael_delivery.backend.domain.Riders;
import com.michael_delivery.backend.model.DestinationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DestinationRepository extends JpaRepository<Destination, Long>,BaseRepository<DestinationDTO,Destination> {

    Destination findFirstByOrder(Orders orders);

    Destination findFirstByDeliveryBy(Riders riders);

}

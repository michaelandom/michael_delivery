package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.Destination;
import com.michael_delivery.backend.domain.Orders;
import com.michael_delivery.backend.domain.Riders;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DestinationRepository extends JpaRepository<Destination, Long> {

    Destination findFirstByOrder(Orders orders);

    Destination findFirstByDeliveryBy(Riders riders);

}

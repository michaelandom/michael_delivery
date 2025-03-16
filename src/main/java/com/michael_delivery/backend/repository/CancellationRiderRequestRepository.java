package com.michael_delivery.backend.repository;

import com.michael_delivery.backend.model.CancellationRiderRequest;
import com.michael_delivery.backend.model.Orders;
import com.michael_delivery.backend.model.Users;
import com.michael_delivery.backend.dto.CancellationRiderRequestDTO;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CancellationRiderRequestRepository extends JpaRepository<CancellationRiderRequest, Long> ,BaseRepository<CancellationRiderRequestDTO,CancellationRiderRequest> {

    CancellationRiderRequest findFirstByOrder(Orders orders);

    CancellationRiderRequest findFirstByCancelledBy(Users users);

}

package com.michael_delivery.backend.repository;

import com.michael_delivery.backend.model.CancellationRequest;
import com.michael_delivery.backend.model.Orders;
import com.michael_delivery.backend.model.Users;
import com.michael_delivery.backend.dto.CancellationRequestDTO;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CancellationRequestRepository extends JpaRepository<CancellationRequest, Long>,BaseRepository<CancellationRequestDTO,CancellationRequest> {

    CancellationRequest findFirstByOrder(Orders orders);

    CancellationRequest findFirstByCancelledBy(Users users);


}

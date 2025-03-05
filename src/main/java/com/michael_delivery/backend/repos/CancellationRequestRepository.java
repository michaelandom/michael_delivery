package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.CancellationRequest;
import com.michael_delivery.backend.domain.Orders;
import com.michael_delivery.backend.domain.Users;
import com.michael_delivery.backend.model.CancellationRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CancellationRequestRepository extends JpaRepository<CancellationRequest, Long> {

    CancellationRequest findFirstByOrder(Orders orders);

    CancellationRequest findFirstByCancelledBy(Users users);

    public Page<CancellationRequestDTO> findAll(Specification<CancellationRequest> spec, Pageable pageable);

}

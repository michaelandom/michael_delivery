package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.CancellationRiderRequest;
import com.michael_delivery.backend.domain.Orders;
import com.michael_delivery.backend.domain.Users;
import com.michael_delivery.backend.model.CancellationRiderRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CancellationRiderRequestRepository extends JpaRepository<CancellationRiderRequest, Long> {

    CancellationRiderRequest findFirstByOrder(Orders orders);

    CancellationRiderRequest findFirstByCancelledBy(Users users);

    public Page<CancellationRiderRequestDTO> findAll(Specification<CancellationRiderRequest> spec, Pageable pageable);

}

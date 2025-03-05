package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.TransportBasicPrices;
import com.michael_delivery.backend.model.TransportBasicPricesDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TransportBasicPricesRepository extends JpaRepository<TransportBasicPrices, Long> {

    TransportBasicPrices findFirstByPreviousAndTransportBasicPriceIdNot(
            TransportBasicPrices transportBasicPrices, final Long transportBasicPriceId);

    public Page<TransportBasicPricesDTO> findAll(Specification<TransportBasicPrices> spec, Pageable pageable);

}

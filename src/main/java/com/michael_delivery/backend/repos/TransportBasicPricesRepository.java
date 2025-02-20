package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.TransportBasicPrices;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TransportBasicPricesRepository extends JpaRepository<TransportBasicPrices, Long> {

    TransportBasicPrices findFirstByPreviousAndTransportBasicPriceIdNot(
            TransportBasicPrices transportBasicPrices, final Long transportBasicPriceId);


}

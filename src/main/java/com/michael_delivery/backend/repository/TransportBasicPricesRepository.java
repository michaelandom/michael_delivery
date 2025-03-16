package com.michael_delivery.backend.repository;

import com.michael_delivery.backend.model.TransportBasicPrices;
import com.michael_delivery.backend.dto.TransportBasicPricesDTO;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TransportBasicPricesRepository extends JpaRepository<TransportBasicPrices, Long>   ,BaseRepository<TransportBasicPricesDTO,TransportBasicPrices>{

    TransportBasicPrices findFirstByPreviousAndTransportBasicPriceIdNot(
            TransportBasicPrices transportBasicPrices, final Long transportBasicPriceId);


}

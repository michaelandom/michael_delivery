package com.michael_delivery.backend.repository;

import com.michael_delivery.backend.model.PickupTimeBasicPrices;
import com.michael_delivery.backend.dto.PickupTimeBasicPricesDTO;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PickupTimeBasicPricesRepository extends JpaRepository<PickupTimeBasicPrices, Long>  ,BaseRepository<PickupTimeBasicPricesDTO,PickupTimeBasicPrices> {

    PickupTimeBasicPrices findFirstByPreviousAndPickupTimeBasicPriceIdNot(
            PickupTimeBasicPrices pickupTimeBasicPrices, final Long pickupTimeBasicPriceId);

//    boolean existsByUniqueVehicleTypePickupTimeCheckIgnoreCase(
//            String uniqueVehicleTypePickupTimeCheck);

}

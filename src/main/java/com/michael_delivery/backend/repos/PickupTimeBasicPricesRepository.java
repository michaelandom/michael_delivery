package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.PickupTimeBasicPrices;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PickupTimeBasicPricesRepository extends JpaRepository<PickupTimeBasicPrices, Long> {

    PickupTimeBasicPrices findFirstByPreviousAndPickupTimeBasicPriceIdNot(
            PickupTimeBasicPrices pickupTimeBasicPrices, final Long pickupTimeBasicPriceId);

//    boolean existsByUniqueVehicleTypePickupTimeCheckIgnoreCase(
//            String uniqueVehicleTypePickupTimeCheck);

}

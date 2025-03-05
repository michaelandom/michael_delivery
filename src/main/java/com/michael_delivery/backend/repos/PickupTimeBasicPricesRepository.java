package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.PickupTimeBasicPrices;
import com.michael_delivery.backend.model.PickupTimeBasicPricesDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PickupTimeBasicPricesRepository extends JpaRepository<PickupTimeBasicPrices, Long>  ,BaseRepository<PickupTimeBasicPricesDTO,PickupTimeBasicPrices> {

    PickupTimeBasicPrices findFirstByPreviousAndPickupTimeBasicPriceIdNot(
            PickupTimeBasicPrices pickupTimeBasicPrices, final Long pickupTimeBasicPriceId);

//    boolean existsByUniqueVehicleTypePickupTimeCheckIgnoreCase(
//            String uniqueVehicleTypePickupTimeCheck);

}

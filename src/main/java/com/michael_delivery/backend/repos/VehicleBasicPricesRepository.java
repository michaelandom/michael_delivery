package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.VehicleBasicPrices;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VehicleBasicPricesRepository extends JpaRepository<VehicleBasicPrices, Long> {

    VehicleBasicPrices findFirstByPreviousAndVehicleBasicPriceIdNot(
            VehicleBasicPrices vehicleBasicPrices, final Long vehicleBasicPriceId);


}

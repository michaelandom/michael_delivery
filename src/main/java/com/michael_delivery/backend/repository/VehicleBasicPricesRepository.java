package com.michael_delivery.backend.repository;

import com.michael_delivery.backend.model.VehicleBasicPrices;
import com.michael_delivery.backend.dto.VehicleBasicPricesDTO;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VehicleBasicPricesRepository extends JpaRepository<VehicleBasicPrices, Long>  ,BaseRepository<VehicleBasicPricesDTO,VehicleBasicPrices> {

    VehicleBasicPrices findFirstByPreviousAndVehicleBasicPriceIdNot(
            VehicleBasicPrices vehicleBasicPrices, final Long vehicleBasicPriceId);
}

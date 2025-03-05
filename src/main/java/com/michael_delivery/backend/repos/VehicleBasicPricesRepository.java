package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.VehicleBasicPrices;
import com.michael_delivery.backend.model.VehicleBasicPricesDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VehicleBasicPricesRepository extends JpaRepository<VehicleBasicPrices, Long>  ,BaseRepository<VehicleBasicPricesDTO,VehicleBasicPrices> {

    VehicleBasicPrices findFirstByPreviousAndVehicleBasicPriceIdNot(
            VehicleBasicPrices vehicleBasicPrices, final Long vehicleBasicPriceId);
}

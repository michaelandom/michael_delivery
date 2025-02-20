package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.Riders;
import com.michael_delivery.backend.domain.Vehicles;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VehiclesRepository extends JpaRepository<Vehicles, Long> {

    Vehicles findFirstByRider(Riders riders);

}

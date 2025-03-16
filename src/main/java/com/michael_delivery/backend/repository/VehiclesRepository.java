package com.michael_delivery.backend.repository;

import com.michael_delivery.backend.model.Riders;
import com.michael_delivery.backend.model.Vehicles;
import com.michael_delivery.backend.dto.VehiclesDTO;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VehiclesRepository extends JpaRepository<Vehicles, Long>  ,BaseRepository<VehiclesDTO,Vehicles> {

    Vehicles findFirstByRider(Riders riders);
}

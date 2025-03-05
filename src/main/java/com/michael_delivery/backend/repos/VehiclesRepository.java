package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.Riders;
import com.michael_delivery.backend.domain.Vehicles;
import com.michael_delivery.backend.model.VehiclesDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VehiclesRepository extends JpaRepository<Vehicles, Long>  ,BaseRepository<VehiclesDTO,Vehicles> {

    Vehicles findFirstByRider(Riders riders);
}

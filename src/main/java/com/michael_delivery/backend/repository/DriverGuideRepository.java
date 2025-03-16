package com.michael_delivery.backend.repository;

import com.michael_delivery.backend.model.DriverGuide;
import com.michael_delivery.backend.dto.DriverGuideDTO;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DriverGuideRepository extends JpaRepository<DriverGuide, Long> ,BaseRepository<DriverGuideDTO,DriverGuide> {

}

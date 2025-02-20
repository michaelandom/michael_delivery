package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.DriverGuide;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DriverGuideRepository extends JpaRepository<DriverGuide, Long> {
}

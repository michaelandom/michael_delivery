package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.DriverGuide;
import com.michael_delivery.backend.model.DriverGuideDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DriverGuideRepository extends JpaRepository<DriverGuide, Long> {
    public Page<DriverGuideDTO> findAll(Specification<DriverGuide> spec, Pageable pageable);

}

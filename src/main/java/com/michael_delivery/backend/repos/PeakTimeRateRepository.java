package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.PeakTimeRate;
import com.michael_delivery.backend.model.PeakTimeRateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PeakTimeRateRepository extends JpaRepository<PeakTimeRate, Long> {

    PeakTimeRate findFirstByPreviousAndPeakTimeRateIdNot(PeakTimeRate peakTimeRate,
            final Long peakTimeRateId);

//    boolean existsByUniquePeakTimeRateCheckIgnoreCase(String uniquePeakTimeRateCheck);
public Page<PeakTimeRateDTO> findAll(Specification<PeakTimeRate> spec, Pageable pageable);

}

package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.PeakTimeRate;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PeakTimeRateRepository extends JpaRepository<PeakTimeRate, Long> {

    PeakTimeRate findFirstByPreviousAndPeakTimeRateIdNot(PeakTimeRate peakTimeRate,
            final Long peakTimeRateId);

//    boolean existsByUniquePeakTimeRateCheckIgnoreCase(String uniquePeakTimeRateCheck);

}

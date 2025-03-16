package com.michael_delivery.backend.repository;

import com.michael_delivery.backend.model.PeakTimeRate;
import com.michael_delivery.backend.dto.PeakTimeRateDTO;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PeakTimeRateRepository extends JpaRepository<PeakTimeRate, Long>  ,BaseRepository<PeakTimeRateDTO,PeakTimeRate> {

    PeakTimeRate findFirstByPreviousAndPeakTimeRateIdNot(PeakTimeRate peakTimeRate,
            final Long peakTimeRateId);

//    boolean existsByUniquePeakTimeRateCheckIgnoreCase(String uniquePeakTimeRateCheck);

}

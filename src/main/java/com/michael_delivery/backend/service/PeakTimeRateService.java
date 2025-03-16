package com.michael_delivery.backend.service;

import com.michael_delivery.backend.model.PeakTimeRate;
import com.michael_delivery.backend.dto.PeakTimeRateDTO;
import com.michael_delivery.backend.repository.PeakTimeRateRepository;
import com.michael_delivery.backend.util.NotFoundException;
import com.michael_delivery.backend.util.ReferencedWarning;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
public class PeakTimeRateService extends BaseService<PeakTimeRate, PeakTimeRateDTO,Long, PeakTimeRateRepository> {

    private final PeakTimeRateRepository peakTimeRateRepository;

    public PeakTimeRateService(final PeakTimeRateRepository peakTimeRateRepository) {
        super(peakTimeRateRepository,"peakTimeRateId");
        this.peakTimeRateRepository = peakTimeRateRepository;
    }

    @Override
    public Page<PeakTimeRateDTO> search(Specification<PeakTimeRate> query, Pageable pageable) {
        return this.peakTimeRateRepository.findAll(query, pageable);
    }

    @Override
    protected PeakTimeRateDTO createDTO() {
        return new PeakTimeRateDTO();
    }

    @Override
    protected PeakTimeRate createEntity() {
        return new PeakTimeRate();
    }

    @Override
    protected PeakTimeRateDTO mapToDTO(final PeakTimeRate peakTimeRate,
            final PeakTimeRateDTO peakTimeRateDTO) {
        peakTimeRateDTO.setPeakTimeRateId(peakTimeRate.getPeakTimeRateId());
        peakTimeRateDTO.setIsWeekend(peakTimeRate.getIsWeekend());
        peakTimeRateDTO.setStartTime(peakTimeRate.getStartTime());
        peakTimeRateDTO.setEndTime(peakTimeRate.getEndTime());
        peakTimeRateDTO.setRate(peakTimeRate.getRate());
        peakTimeRateDTO.setIsLatest(peakTimeRate.getIsLatest());
        peakTimeRateDTO.setIsDeleted(peakTimeRate.getIsDeleted());
        peakTimeRateDTO.setPrevious(peakTimeRate.getPrevious() == null ? null : peakTimeRate.getPrevious().getPeakTimeRateId());
        return peakTimeRateDTO;
    }

    @Override
    protected PeakTimeRate mapToEntity(final PeakTimeRateDTO peakTimeRateDTO,
            final PeakTimeRate peakTimeRate) {
        peakTimeRate.setIsWeekend(peakTimeRateDTO.getIsWeekend());
        peakTimeRate.setStartTime(peakTimeRateDTO.getStartTime());
        peakTimeRate.setEndTime(peakTimeRateDTO.getEndTime());
        peakTimeRate.setRate(peakTimeRateDTO.getRate());
        peakTimeRate.setIsLatest(peakTimeRateDTO.getIsLatest());
        peakTimeRate.setIsDeleted(peakTimeRateDTO.getIsDeleted());
        final PeakTimeRate previous = peakTimeRateDTO.getPrevious() == null ? null : peakTimeRateRepository.findById(peakTimeRateDTO.getPrevious())
                .orElseThrow(() -> new NotFoundException("previous not found"));
        peakTimeRate.setPrevious(previous);
        return peakTimeRate;
    }


    public ReferencedWarning getReferencedWarning(final Long peakTimeRateId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final PeakTimeRate peakTimeRate = peakTimeRateRepository.findById(peakTimeRateId)
                .orElseThrow(NotFoundException::new);
        final PeakTimeRate previousPeakTimeRate = peakTimeRateRepository.findFirstByPreviousAndPeakTimeRateIdNot(peakTimeRate, peakTimeRate.getPeakTimeRateId());
        if (previousPeakTimeRate != null) {
            referencedWarning.setKey("peakTimeRate.peakTimeRate.previous.referenced");
            referencedWarning.addParam(previousPeakTimeRate.getPeakTimeRateId());
            return referencedWarning;
        }
        return null;
    }

}

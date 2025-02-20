package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.PeakTimeRate;
import com.michael_delivery.backend.model.PeakTimeRateDTO;
import com.michael_delivery.backend.repos.PeakTimeRateRepository;
import com.michael_delivery.backend.util.NotFoundException;
import com.michael_delivery.backend.util.ReferencedWarning;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PeakTimeRateService {

    private final PeakTimeRateRepository peakTimeRateRepository;

    public PeakTimeRateService(final PeakTimeRateRepository peakTimeRateRepository) {
        this.peakTimeRateRepository = peakTimeRateRepository;
    }

    public List<PeakTimeRateDTO> findAll() {
        final List<PeakTimeRate> peakTimeRates = peakTimeRateRepository.findAll(Sort.by("peakTimeRateId"));
        return peakTimeRates.stream()
                .map(peakTimeRate -> mapToDTO(peakTimeRate, new PeakTimeRateDTO()))
                .toList();
    }

    public PeakTimeRateDTO get(final Long peakTimeRateId) {
        return peakTimeRateRepository.findById(peakTimeRateId)
                .map(peakTimeRate -> mapToDTO(peakTimeRate, new PeakTimeRateDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final PeakTimeRateDTO peakTimeRateDTO) {
        final PeakTimeRate peakTimeRate = new PeakTimeRate();
        mapToEntity(peakTimeRateDTO, peakTimeRate);
        return peakTimeRateRepository.save(peakTimeRate).getPeakTimeRateId();
    }

    public void update(final Long peakTimeRateId, final PeakTimeRateDTO peakTimeRateDTO) {
        final PeakTimeRate peakTimeRate = peakTimeRateRepository.findById(peakTimeRateId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(peakTimeRateDTO, peakTimeRate);
        peakTimeRateRepository.save(peakTimeRate);
    }

    public void delete(final Long peakTimeRateId) {
        peakTimeRateRepository.deleteById(peakTimeRateId);
    }

    private PeakTimeRateDTO mapToDTO(final PeakTimeRate peakTimeRate,
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

    private PeakTimeRate mapToEntity(final PeakTimeRateDTO peakTimeRateDTO,
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

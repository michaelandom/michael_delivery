package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.RiderCommission;
import com.michael_delivery.backend.model.RiderCommissionDTO;
import com.michael_delivery.backend.repos.RiderCommissionRepository;
import com.michael_delivery.backend.util.NotFoundException;
import com.michael_delivery.backend.util.ReferencedWarning;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RiderCommissionService {

    private final RiderCommissionRepository riderCommissionRepository;

    public RiderCommissionService(final RiderCommissionRepository riderCommissionRepository) {
        this.riderCommissionRepository = riderCommissionRepository;
    }

    public List<RiderCommissionDTO> findAll() {
        final List<RiderCommission> riderCommissions = riderCommissionRepository.findAll(Sort.by("riderCommissionId"));
        return riderCommissions.stream()
                .map(riderCommission -> mapToDTO(riderCommission, new RiderCommissionDTO()))
                .toList();
    }

    public RiderCommissionDTO get(final Long riderCommissionId) {
        return riderCommissionRepository.findById(riderCommissionId)
                .map(riderCommission -> mapToDTO(riderCommission, new RiderCommissionDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final RiderCommissionDTO riderCommissionDTO) {
        final RiderCommission riderCommission = new RiderCommission();
        mapToEntity(riderCommissionDTO, riderCommission);
        return riderCommissionRepository.save(riderCommission).getRiderCommissionId();
    }

    public void update(final Long riderCommissionId, final RiderCommissionDTO riderCommissionDTO) {
        final RiderCommission riderCommission = riderCommissionRepository.findById(riderCommissionId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(riderCommissionDTO, riderCommission);
        riderCommissionRepository.save(riderCommission);
    }

    public void delete(final Long riderCommissionId) {
        riderCommissionRepository.deleteById(riderCommissionId);
    }

    private RiderCommissionDTO mapToDTO(final RiderCommission riderCommission,
            final RiderCommissionDTO riderCommissionDTO) {
        riderCommissionDTO.setRiderCommissionId(riderCommission.getRiderCommissionId());
        riderCommissionDTO.setBasicCommission(riderCommission.getBasicCommission());
        riderCommissionDTO.setOvertimeRate(riderCommission.getOvertimeRate());
        riderCommissionDTO.setHolidayRate(riderCommission.getHolidayRate());
        riderCommissionDTO.setIsLatest(riderCommission.getIsLatest());
        riderCommissionDTO.setPrevious(riderCommission.getPrevious() == null ? null : riderCommission.getPrevious().getRiderCommissionId());
        return riderCommissionDTO;
    }

    private RiderCommission mapToEntity(final RiderCommissionDTO riderCommissionDTO,
            final RiderCommission riderCommission) {
        riderCommission.setBasicCommission(riderCommissionDTO.getBasicCommission());
        riderCommission.setOvertimeRate(riderCommissionDTO.getOvertimeRate());
        riderCommission.setHolidayRate(riderCommissionDTO.getHolidayRate());
        riderCommission.setIsLatest(riderCommissionDTO.getIsLatest());
        final RiderCommission previous = riderCommissionDTO.getPrevious() == null ? null : riderCommissionRepository.findById(riderCommissionDTO.getPrevious())
                .orElseThrow(() -> new NotFoundException("previous not found"));
        riderCommission.setPrevious(previous);
        return riderCommission;
    }



    public ReferencedWarning getReferencedWarning(final Long riderCommissionId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final RiderCommission riderCommission = riderCommissionRepository.findById(riderCommissionId)
                .orElseThrow(NotFoundException::new);
        final RiderCommission previousRiderCommission = riderCommissionRepository.findFirstByPreviousAndRiderCommissionIdNot(riderCommission, riderCommission.getRiderCommissionId());
        if (previousRiderCommission != null) {
            referencedWarning.setKey("riderCommission.riderCommission.previous.referenced");
            referencedWarning.addParam(previousRiderCommission.getRiderCommissionId());
            return referencedWarning;
        }
        return null;
    }

}

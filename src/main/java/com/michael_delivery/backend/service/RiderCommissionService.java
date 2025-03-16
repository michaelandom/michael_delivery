package com.michael_delivery.backend.service;

import com.michael_delivery.backend.model.RiderCommission;
import com.michael_delivery.backend.dto.RiderCommissionDTO;
import com.michael_delivery.backend.repository.RiderCommissionRepository;
import com.michael_delivery.backend.util.NotFoundException;
import com.michael_delivery.backend.util.ReferencedWarning;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
public class RiderCommissionService extends BaseService<RiderCommission, RiderCommissionDTO,Long, RiderCommissionRepository> {

    private final RiderCommissionRepository riderCommissionRepository;

    public RiderCommissionService(final RiderCommissionRepository riderCommissionRepository) {
        super(riderCommissionRepository,"riderCommissionId");

        this.riderCommissionRepository = riderCommissionRepository;
    }


    @Override
    public Page<RiderCommissionDTO> search(Specification<RiderCommission> query, Pageable pageable) {
        return this.riderCommissionRepository.findAll(query, pageable);
    }
    @Override
    protected RiderCommissionDTO mapToDTO(final RiderCommission riderCommission,
            final RiderCommissionDTO riderCommissionDTO) {
        riderCommissionDTO.setRiderCommissionId(riderCommission.getRiderCommissionId());
        riderCommissionDTO.setBasicCommission(riderCommission.getBasicCommission());
        riderCommissionDTO.setOvertimeRate(riderCommission.getOvertimeRate());
        riderCommissionDTO.setHolidayRate(riderCommission.getHolidayRate());
        riderCommissionDTO.setIsLatest(riderCommission.getIsLatest());
        riderCommissionDTO.setPrevious(riderCommission.getPrevious() == null ? null : riderCommission.getPrevious().getRiderCommissionId());
        return riderCommissionDTO;
    }

    @Override
    protected RiderCommission mapToEntity(final RiderCommissionDTO riderCommissionDTO,
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

    @Override
    protected RiderCommissionDTO createDTO() {
        return new RiderCommissionDTO();
    }

    @Override
    protected RiderCommission createEntity() {
        return new RiderCommission();
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

package com.michael_delivery.backend.service;

import com.michael_delivery.backend.model.DriverGuide;
import com.michael_delivery.backend.dto.DriverGuideDTO;
import com.michael_delivery.backend.repository.DriverGuideRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
public class DriverGuideService extends BaseService<DriverGuide, DriverGuideDTO,Long, DriverGuideRepository>{

    private final DriverGuideRepository driverGuideRepository;

    public DriverGuideService(final DriverGuideRepository driverGuideRepository) {
        super(driverGuideRepository,"driverGuideId");
        this.driverGuideRepository = driverGuideRepository;
    }

    @Override
    protected DriverGuideDTO createDTO() {
        return new DriverGuideDTO();
    }

    @Override
    protected DriverGuide createEntity() {
        return new DriverGuide();
    }

    @Override
    public Page<DriverGuideDTO> search(Specification<DriverGuide> query, Pageable pageable) {
        return this.driverGuideRepository.findAll(query, pageable);
    }

    @Override
    protected DriverGuideDTO mapToDTO(final DriverGuide driverGuide,
            final DriverGuideDTO driverGuideDTO) {
        driverGuideDTO.setDriverGuideId(driverGuide.getDriverGuideId());
        driverGuideDTO.setFileUrl(driverGuide.getFileUrl());
        driverGuideDTO.setDescription(driverGuide.getDescription());
        driverGuideDTO.setIsImportant(driverGuide.getIsImportant());
        return driverGuideDTO;
    }

    @Override
    protected DriverGuide mapToEntity(final DriverGuideDTO driverGuideDTO,
            final DriverGuide driverGuide) {
        driverGuide.setFileUrl(driverGuideDTO.getFileUrl());
        driverGuide.setDescription(driverGuideDTO.getDescription());
        driverGuide.setIsImportant(driverGuideDTO.getIsImportant());
        return driverGuide;
    }

}

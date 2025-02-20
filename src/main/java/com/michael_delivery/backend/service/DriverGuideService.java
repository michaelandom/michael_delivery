package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.DriverGuide;
import com.michael_delivery.backend.model.DriverGuideDTO;
import com.michael_delivery.backend.repos.DriverGuideRepository;
import com.michael_delivery.backend.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DriverGuideService {

    private final DriverGuideRepository driverGuideRepository;

    public DriverGuideService(final DriverGuideRepository driverGuideRepository) {
        this.driverGuideRepository = driverGuideRepository;
    }

    public List<DriverGuideDTO> findAll() {
        final List<DriverGuide> driverGuides = driverGuideRepository.findAll(Sort.by("driverGuideId"));
        return driverGuides.stream()
                .map(driverGuide -> mapToDTO(driverGuide, new DriverGuideDTO()))
                .toList();
    }

    public DriverGuideDTO get(final Long driverGuideId) {
        return driverGuideRepository.findById(driverGuideId)
                .map(driverGuide -> mapToDTO(driverGuide, new DriverGuideDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final DriverGuideDTO driverGuideDTO) {
        final DriverGuide driverGuide = new DriverGuide();
        mapToEntity(driverGuideDTO, driverGuide);
        return driverGuideRepository.save(driverGuide).getDriverGuideId();
    }

    public void update(final Long driverGuideId, final DriverGuideDTO driverGuideDTO) {
        final DriverGuide driverGuide = driverGuideRepository.findById(driverGuideId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(driverGuideDTO, driverGuide);
        driverGuideRepository.save(driverGuide);
    }

    public void delete(final Long driverGuideId) {
        driverGuideRepository.deleteById(driverGuideId);
    }

    private DriverGuideDTO mapToDTO(final DriverGuide driverGuide,
            final DriverGuideDTO driverGuideDTO) {
        driverGuideDTO.setDriverGuideId(driverGuide.getDriverGuideId());
        driverGuideDTO.setFileUrl(driverGuide.getFileUrl());
        driverGuideDTO.setDescription(driverGuide.getDescription());
        driverGuideDTO.setIsImportant(driverGuide.getIsImportant());
        return driverGuideDTO;
    }

    private DriverGuide mapToEntity(final DriverGuideDTO driverGuideDTO,
            final DriverGuide driverGuide) {
        driverGuide.setFileUrl(driverGuideDTO.getFileUrl());
        driverGuide.setDescription(driverGuideDTO.getDescription());
        driverGuide.setIsImportant(driverGuideDTO.getIsImportant());
        return driverGuide;
    }

}

package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.ServiceArea;
import com.michael_delivery.backend.domain.State;
import com.michael_delivery.backend.model.ServiceAreaDTO;
import com.michael_delivery.backend.repos.ServiceAreaRepository;
import com.michael_delivery.backend.repos.StateRepository;
import com.michael_delivery.backend.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ServiceAreaService {

    private final ServiceAreaRepository serviceAreaRepository;
    private final StateRepository stateRepository;

    public ServiceAreaService(final ServiceAreaRepository serviceAreaRepository,
            final StateRepository stateRepository) {
        this.serviceAreaRepository = serviceAreaRepository;
        this.stateRepository = stateRepository;
    }

    public List<ServiceAreaDTO> findAll() {
        final List<ServiceArea> serviceAreas = serviceAreaRepository.findAll(Sort.by("serviceAreaId"));
        return serviceAreas.stream()
                .map(serviceArea -> mapToDTO(serviceArea, new ServiceAreaDTO()))
                .toList();
    }

    public ServiceAreaDTO get(final Long serviceAreaId) {
        return serviceAreaRepository.findById(serviceAreaId)
                .map(serviceArea -> mapToDTO(serviceArea, new ServiceAreaDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ServiceAreaDTO serviceAreaDTO) {
        final ServiceArea serviceArea = new ServiceArea();
        mapToEntity(serviceAreaDTO, serviceArea);
        return serviceAreaRepository.save(serviceArea).getServiceAreaId();
    }

    public void update(final Long serviceAreaId, final ServiceAreaDTO serviceAreaDTO) {
        final ServiceArea serviceArea = serviceAreaRepository.findById(serviceAreaId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(serviceAreaDTO, serviceArea);
        serviceAreaRepository.save(serviceArea);
    }

    public void delete(final Long serviceAreaId) {
        serviceAreaRepository.deleteById(serviceAreaId);
    }

    private ServiceAreaDTO mapToDTO(final ServiceArea serviceArea,
            final ServiceAreaDTO serviceAreaDTO) {
        serviceAreaDTO.setServiceAreaId(serviceArea.getServiceAreaId());
        serviceAreaDTO.setName(serviceArea.getName());
        serviceAreaDTO.setCode(serviceArea.getCode());
        serviceAreaDTO.setIsActive(serviceArea.getIsActive());
        serviceAreaDTO.setStateName(serviceArea.getStateName() == null ? null : serviceArea.getStateName().getStateId());
        return serviceAreaDTO;
    }

    private ServiceArea mapToEntity(final ServiceAreaDTO serviceAreaDTO,
            final ServiceArea serviceArea) {
        serviceArea.setName(serviceAreaDTO.getName());
        serviceArea.setCode(serviceAreaDTO.getCode());
        serviceArea.setIsActive(serviceAreaDTO.getIsActive());
        final State stateName = serviceAreaDTO.getStateName() == null ? null : stateRepository.findById(serviceAreaDTO.getStateName())
                .orElseThrow(() -> new NotFoundException("stateName not found"));
        serviceArea.setStateName(stateName);
        return serviceArea;
    }

}

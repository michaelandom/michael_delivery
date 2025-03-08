package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.CancellationRiderRequest;
import com.michael_delivery.backend.domain.Destination;
import com.michael_delivery.backend.domain.ServiceArea;
import com.michael_delivery.backend.domain.State;
import com.michael_delivery.backend.model.CancellationRiderRequestDTO;
import com.michael_delivery.backend.model.DestinationDTO;
import com.michael_delivery.backend.model.ServiceAreaDTO;
import com.michael_delivery.backend.repos.CancellationRiderRequestRepository;
import com.michael_delivery.backend.repos.ServiceAreaRepository;
import com.michael_delivery.backend.repos.StateRepository;
import com.michael_delivery.backend.util.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ServiceAreaService extends BaseService<ServiceArea, ServiceAreaDTO,Long, ServiceAreaRepository>{

    private final ServiceAreaRepository serviceAreaRepository;
    private final StateRepository stateRepository;

    public ServiceAreaService(final ServiceAreaRepository serviceAreaRepository,
            final StateRepository stateRepository) {
        super(serviceAreaRepository,"serviceAreaId");

        this.serviceAreaRepository = serviceAreaRepository;
        this.stateRepository = stateRepository;
    }



    @Override
    public Page<ServiceAreaDTO> search(Specification<ServiceArea> query, Pageable pageable) {
        return this.serviceAreaRepository.findAll(query, pageable);
    }

    @Override
    protected ServiceAreaDTO mapToDTO(final ServiceArea serviceArea,
            final ServiceAreaDTO serviceAreaDTO) {
        serviceAreaDTO.setServiceAreaId(serviceArea.getServiceAreaId());
        serviceAreaDTO.setName(serviceArea.getName());
        serviceAreaDTO.setCode(serviceArea.getCode());
        serviceAreaDTO.setIsActive(serviceArea.getIsActive());
        serviceAreaDTO.setStateId(serviceArea.getState() == null ? null : serviceArea.getState().getStateId());
        return serviceAreaDTO;
    }

    @Override
    protected ServiceArea mapToEntity(final ServiceAreaDTO serviceAreaDTO,
            final ServiceArea serviceArea) {
        serviceArea.setName(serviceAreaDTO.getName());
        serviceArea.setCode(serviceAreaDTO.getCode());
        serviceArea.setIsActive(serviceAreaDTO.getIsActive());
        final State state = serviceAreaDTO.getStateId() == null ? null : stateRepository.findById(serviceAreaDTO.getStateId())
                .orElseThrow(() -> new NotFoundException("State Not Found"));
        serviceArea.setState(state);
        return serviceArea;
    }

    @Override
    protected ServiceAreaDTO createDTO() {
        return new ServiceAreaDTO();
    }

    @Override
    protected ServiceArea createEntity() {
        return new ServiceArea();
    }

}

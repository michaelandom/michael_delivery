package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.CancellationRiderRequest;
import com.michael_delivery.backend.domain.Destination;
import com.michael_delivery.backend.domain.ServiceArea;
import com.michael_delivery.backend.domain.State;
import com.michael_delivery.backend.model.CancellationRiderRequestDTO;
import com.michael_delivery.backend.model.DestinationDTO;
import com.michael_delivery.backend.model.StateDTO;
import com.michael_delivery.backend.repos.CancellationRiderRequestRepository;
import com.michael_delivery.backend.repos.ServiceAreaRepository;
import com.michael_delivery.backend.repos.StateRepository;
import com.michael_delivery.backend.util.NotFoundException;
import com.michael_delivery.backend.util.ReferencedWarning;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class StateService extends BaseService<State, StateDTO,Long, StateRepository> {

    private final StateRepository stateRepository;
    private final ServiceAreaRepository serviceAreaRepository;

    public StateService(final StateRepository stateRepository,
            final ServiceAreaRepository serviceAreaRepository) {
        super(stateRepository,"stateId");
        this.stateRepository = stateRepository;
        this.serviceAreaRepository = serviceAreaRepository;
    }

    @Override
    public Page<StateDTO> search(Specification<State> query, Pageable pageable) {
        return this.stateRepository.findAll(query, pageable);
    }

    @Override
    protected StateDTO mapToDTO(final State state, final StateDTO stateDTO) {
        stateDTO.setStateId(state.getStateId());
        stateDTO.setName(state.getName());
        stateDTO.setCode(state.getCode());
        stateDTO.setLogoUrl(state.getLogoUrl());
        return stateDTO;
    }

    @Override
    protected State mapToEntity(final StateDTO stateDTO, final State state) {
        state.setName(stateDTO.getName());
        state.setCode(stateDTO.getCode());
        state.setLogoUrl(stateDTO.getLogoUrl());
        return state;
    }

    @Override
    protected StateDTO createDTO() {
        return new StateDTO();
    }

    @Override
    protected State createEntity() {
        return new State();
    }

    public ReferencedWarning getReferencedWarning(final Long stateId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final State state = stateRepository.findById(stateId)
                .orElseThrow(NotFoundException::new);
        final ServiceArea stateNameServiceArea = serviceAreaRepository.findFirstByState(state);
        if (stateNameServiceArea != null) {
            referencedWarning.setKey("state.serviceArea.state.referenced");
            referencedWarning.addParam(stateNameServiceArea.getServiceAreaId());
            return referencedWarning;
        }
        return null;
    }

}

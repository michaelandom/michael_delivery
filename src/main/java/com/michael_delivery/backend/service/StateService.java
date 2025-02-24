package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.ServiceArea;
import com.michael_delivery.backend.domain.State;
import com.michael_delivery.backend.model.StateDTO;
import com.michael_delivery.backend.repos.ServiceAreaRepository;
import com.michael_delivery.backend.repos.StateRepository;
import com.michael_delivery.backend.util.NotFoundException;
import com.michael_delivery.backend.util.ReferencedWarning;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class StateService {

    private final StateRepository stateRepository;
    private final ServiceAreaRepository serviceAreaRepository;

    public StateService(final StateRepository stateRepository,
            final ServiceAreaRepository serviceAreaRepository) {
        this.stateRepository = stateRepository;
        this.serviceAreaRepository = serviceAreaRepository;
    }

    public List<StateDTO> findAll() {
        final List<State> states = stateRepository.findAll(Sort.by("stateId"));
        return states.stream()
                .map(state -> mapToDTO(state, new StateDTO()))
                .toList();
    }

    public StateDTO get(final Long stateId) {
        return stateRepository.findById(stateId)
                .map(state -> mapToDTO(state, new StateDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final StateDTO stateDTO) {
        final State state = new State();
        mapToEntity(stateDTO, state);
        return stateRepository.save(state).getStateId();
    }

    public void update(final Long stateId, final StateDTO stateDTO) {
        final State state = stateRepository.findById(stateId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(stateDTO, state);
        stateRepository.save(state);
    }

    public void delete(final Long stateId) {
        stateRepository.deleteById(stateId);
    }

    private StateDTO mapToDTO(final State state, final StateDTO stateDTO) {
        stateDTO.setStateId(state.getStateId());
        stateDTO.setName(state.getName());
        stateDTO.setCode(state.getCode());
        stateDTO.setLogoUrl(state.getLogoUrl());
        return stateDTO;
    }

    private State mapToEntity(final StateDTO stateDTO, final State state) {
        state.setName(stateDTO.getName());
        state.setCode(stateDTO.getCode());
        state.setLogoUrl(stateDTO.getLogoUrl());
        return state;
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

package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.CancellationRiderRequest;
import com.michael_delivery.backend.domain.Destination;
import com.michael_delivery.backend.domain.Reference;
import com.michael_delivery.backend.model.CancellationRiderRequestDTO;
import com.michael_delivery.backend.model.DestinationDTO;
import com.michael_delivery.backend.model.ReferenceDTO;
import com.michael_delivery.backend.repos.CancellationRiderRequestRepository;
import com.michael_delivery.backend.repos.ReferenceRepository;
import com.michael_delivery.backend.util.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ReferenceService extends BaseService<Reference, ReferenceDTO,Long, ReferenceRepository>{

    private final ReferenceRepository referenceRepository;

    public ReferenceService(final ReferenceRepository referenceRepository) {
        super(referenceRepository,"referenceId");

        this.referenceRepository = referenceRepository;
    }

    @Override
    public Page<ReferenceDTO> search(Specification<Reference> query, Pageable pageable) {
        return this.referenceRepository.findAll(query, pageable);
    }
    @Override
    protected ReferenceDTO mapToDTO(final Reference reference, final ReferenceDTO referenceDTO) {
        referenceDTO.setReferenceId(reference.getReferenceId());
//        referenceDTO.setOrderIds(reference.getOrderIds());
        referenceDTO.setAmount(reference.getAmount());
        referenceDTO.setCurrency(reference.getCurrency());
        referenceDTO.setPspReference(reference.getPspReference());
        referenceDTO.setPaymentMethod(reference.getPaymentMethod());
        referenceDTO.setResultJson(reference.getResultJson());
        return referenceDTO;
    }

    @Override
    protected Reference mapToEntity(final ReferenceDTO referenceDTO, final Reference reference) {
//        reference.setOrderIds(referenceDTO.getOrderIds());
        reference.setAmount(referenceDTO.getAmount());
        reference.setCurrency(referenceDTO.getCurrency());
        reference.setPspReference(referenceDTO.getPspReference());
        reference.setPaymentMethod(referenceDTO.getPaymentMethod());
        reference.setResultJson(referenceDTO.getResultJson());
        return reference;
    }

    @Override
    protected ReferenceDTO createDTO() {
        return new ReferenceDTO();
    }

    @Override
    protected Reference createEntity() {
        return new Reference();
    }

}

package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.Reference;
import com.michael_delivery.backend.model.ReferenceDTO;
import com.michael_delivery.backend.repos.ReferenceRepository;
import com.michael_delivery.backend.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ReferenceService {

    private final ReferenceRepository referenceRepository;

    public ReferenceService(final ReferenceRepository referenceRepository) {
        this.referenceRepository = referenceRepository;
    }

    public List<ReferenceDTO> findAll() {
        final List<Reference> references = referenceRepository.findAll(Sort.by("referenceId"));
        return references.stream()
                .map(reference -> mapToDTO(reference, new ReferenceDTO()))
                .toList();
    }

    public ReferenceDTO get(final Long referenceId) {
        return referenceRepository.findById(referenceId)
                .map(reference -> mapToDTO(reference, new ReferenceDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ReferenceDTO referenceDTO) {
        final Reference reference = new Reference();
        mapToEntity(referenceDTO, reference);
        return referenceRepository.save(reference).getReferenceId();
    }

    public void update(final Long referenceId, final ReferenceDTO referenceDTO) {
        final Reference reference = referenceRepository.findById(referenceId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(referenceDTO, reference);
        referenceRepository.save(reference);
    }

    public void delete(final Long referenceId) {
        referenceRepository.deleteById(referenceId);
    }

    private ReferenceDTO mapToDTO(final Reference reference, final ReferenceDTO referenceDTO) {
        referenceDTO.setReferenceId(reference.getReferenceId());
//        referenceDTO.setOrderIds(reference.getOrderIds());
        referenceDTO.setAmount(reference.getAmount());
        referenceDTO.setCurrency(reference.getCurrency());
        referenceDTO.setPspReference(reference.getPspReference());
        referenceDTO.setPaymentMethod(reference.getPaymentMethod());
        referenceDTO.setResultJson(reference.getResultJson());
        return referenceDTO;
    }

    private Reference mapToEntity(final ReferenceDTO referenceDTO, final Reference reference) {
//        reference.setOrderIds(referenceDTO.getOrderIds());
        reference.setAmount(referenceDTO.getAmount());
        reference.setCurrency(referenceDTO.getCurrency());
        reference.setPspReference(referenceDTO.getPspReference());
        reference.setPaymentMethod(referenceDTO.getPaymentMethod());
        reference.setResultJson(referenceDTO.getResultJson());
        return reference;
    }

}

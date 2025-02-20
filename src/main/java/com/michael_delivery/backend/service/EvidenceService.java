package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.Destination;
import com.michael_delivery.backend.domain.Evidence;
import com.michael_delivery.backend.model.EvidenceDTO;
import com.michael_delivery.backend.repos.DestinationRepository;
import com.michael_delivery.backend.repos.EvidenceRepository;
import com.michael_delivery.backend.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EvidenceService {

    private final EvidenceRepository evidenceRepository;
    private final DestinationRepository destinationRepository;

    public EvidenceService(final EvidenceRepository evidenceRepository,
            final DestinationRepository destinationRepository) {
        this.evidenceRepository = evidenceRepository;
        this.destinationRepository = destinationRepository;
    }

    public List<EvidenceDTO> findAll() {
        final List<Evidence> evidences = evidenceRepository.findAll(Sort.by("evidenceId"));
        return evidences.stream()
                .map(evidence -> mapToDTO(evidence, new EvidenceDTO()))
                .toList();
    }

    public EvidenceDTO get(final Long evidenceId) {
        return evidenceRepository.findById(evidenceId)
                .map(evidence -> mapToDTO(evidence, new EvidenceDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final EvidenceDTO evidenceDTO) {
        final Evidence evidence = new Evidence();
        mapToEntity(evidenceDTO, evidence);
        return evidenceRepository.save(evidence).getEvidenceId();
    }

    public void update(final Long evidenceId, final EvidenceDTO evidenceDTO) {
        final Evidence evidence = evidenceRepository.findById(evidenceId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(evidenceDTO, evidence);
        evidenceRepository.save(evidence);
    }

    public void delete(final Long evidenceId) {
        evidenceRepository.deleteById(evidenceId);
    }

    private EvidenceDTO mapToDTO(final Evidence evidence, final EvidenceDTO evidenceDTO) {
        evidenceDTO.setEvidenceId(evidence.getEvidenceId());
        evidenceDTO.setUrls(evidence.getUrls());
        evidenceDTO.setRecipientName(evidence.getRecipientName());
        evidenceDTO.setRecipientDob(evidence.getRecipientDob());
        evidenceDTO.setNote(evidence.getNote());
        evidenceDTO.setTime(evidence.getTime());
        evidenceDTO.setDestination(evidence.getDestination() == null ? null : evidence.getDestination().getDestinationId());
        return evidenceDTO;
    }

    private Evidence mapToEntity(final EvidenceDTO evidenceDTO, final Evidence evidence) {
        evidence.setUrls(evidenceDTO.getUrls());
        evidence.setRecipientName(evidenceDTO.getRecipientName());
        evidence.setRecipientDob(evidenceDTO.getRecipientDob());
        evidence.setNote(evidenceDTO.getNote());
        evidence.setTime(evidenceDTO.getTime());
        final Destination destination = evidenceDTO.getDestination() == null ? null : destinationRepository.findById(evidenceDTO.getDestination())
                .orElseThrow(() -> new NotFoundException("destination not found"));
        evidence.setDestination(destination);
        return evidence;
    }

}

package com.michael_delivery.backend.service;

import com.michael_delivery.backend.model.Destination;
import com.michael_delivery.backend.model.Evidence;
import com.michael_delivery.backend.dto.EvidenceDTO;
import com.michael_delivery.backend.repository.DestinationRepository;
import com.michael_delivery.backend.repository.EvidenceRepository;
import com.michael_delivery.backend.util.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
public class EvidenceService extends BaseService<Evidence, EvidenceDTO,Long, EvidenceRepository>  {

    private final EvidenceRepository evidenceRepository;
    private final DestinationRepository destinationRepository;

    public EvidenceService(final EvidenceRepository evidenceRepository,
            final DestinationRepository destinationRepository) {
        super(evidenceRepository,"evidenceId");
        this.evidenceRepository = evidenceRepository;
        this.destinationRepository = destinationRepository;
    }

    @Override
    public Page<EvidenceDTO> search(Specification<Evidence> query, Pageable pageable) {
        return this.evidenceRepository.findAll(query, pageable);
    }
@Override
    protected EvidenceDTO mapToDTO(final Evidence evidence, final EvidenceDTO evidenceDTO) {
        evidenceDTO.setEvidenceId(evidence.getEvidenceId());
        evidenceDTO.setUrls(evidence.getUrls());
        evidenceDTO.setRecipientName(evidence.getRecipientName());
        evidenceDTO.setRecipientDob(evidence.getRecipientDob());
        evidenceDTO.setNote(evidence.getNote());
        evidenceDTO.setTime(evidence.getTime());
        evidenceDTO.setDestination(evidence.getDestination() == null ? null : evidence.getDestination().getDestinationId());
        return evidenceDTO;
    }

    @Override
    protected Evidence mapToEntity(final EvidenceDTO evidenceDTO, final Evidence evidence) {
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

    @Override
    protected EvidenceDTO createDTO() {
        return new EvidenceDTO();
    }

    @Override
    protected Evidence createEntity() {
        return new Evidence();
    }

}

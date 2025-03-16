package com.michael_delivery.backend.service;

import com.michael_delivery.backend.model.*;
import com.michael_delivery.backend.dto.DestinationDTO;
import com.michael_delivery.backend.repository.*;
import com.michael_delivery.backend.util.NotFoundException;
import com.michael_delivery.backend.util.ReferencedWarning;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
public class DestinationService  extends BaseService<Destination, DestinationDTO,Long, DestinationRepository>{

    private final DestinationRepository destinationRepository;
    private final OrdersRepository ordersRepository;
    private final RidersRepository ridersRepository;
    private final EvidenceRepository evidenceRepository;
    private final ItemRepository itemRepository;
    private final NoteDestinationRepository noteDestinationRepository;

    public DestinationService(final DestinationRepository destinationRepository,
            final OrdersRepository ordersRepository, final RidersRepository ridersRepository,
            final EvidenceRepository evidenceRepository, final ItemRepository itemRepository,
            final NoteDestinationRepository noteDestinationRepository) {
        super(destinationRepository,"destinationId");
        this.destinationRepository = destinationRepository;
        this.ordersRepository = ordersRepository;
        this.ridersRepository = ridersRepository;
        this.evidenceRepository = evidenceRepository;
        this.itemRepository = itemRepository;
        this.noteDestinationRepository = noteDestinationRepository;
    }

    @Override
    public Page<DestinationDTO> search(Specification<Destination> query, Pageable pageable) {
        return this.destinationRepository.findAll(query, pageable);
    }
    protected DestinationDTO mapToDTO(final Destination destination,
            final DestinationDTO destinationDTO) {
        destinationDTO.setDestinationId(destination.getDestinationId());
        destinationDTO.setDestinationLatitude(destination.getDestinationLatitude());
        destinationDTO.setDestinationLongitude(destination.getDestinationLongitude());
        destinationDTO.setDestinationAddressText(destination.getDestinationAddressText());
        destinationDTO.setSequence(destination.getSequence());
        destinationDTO.setRecipientPhoneNumber(destination.getRecipientPhoneNumber());
        destinationDTO.setPrice(destination.getPrice());
        destinationDTO.setEstimatedTime(destination.getEstimatedTime());
        destinationDTO.setSafeStorage(destination.getSafeStorage());
        destinationDTO.setSpecificRecipient(destination.getSpecificRecipient());
        destinationDTO.setRecipientName(destination.getRecipientName());
        destinationDTO.setStatus(destination.getStatus());
        destinationDTO.setOrder(destination.getOrder() == null ? null : destination.getOrder().getOrderId());
        destinationDTO.setDeliveryBy(destination.getDeliveryBy() == null ? null : destination.getDeliveryBy().getRiderId());
        return destinationDTO;
    }

    protected Destination mapToEntity(final DestinationDTO destinationDTO,
            final Destination destination) {
        destination.setDestinationLatitude(destinationDTO.getDestinationLatitude());
        destination.setDestinationLongitude(destinationDTO.getDestinationLongitude());
        destination.setDestinationAddressText(destinationDTO.getDestinationAddressText());
        destination.setSequence(destinationDTO.getSequence());
        destination.setRecipientPhoneNumber(destinationDTO.getRecipientPhoneNumber());
        destination.setPrice(destinationDTO.getPrice());
        destination.setEstimatedTime(destinationDTO.getEstimatedTime());
        destination.setSafeStorage(destinationDTO.getSafeStorage());
        destination.setSpecificRecipient(destinationDTO.getSpecificRecipient());
        destination.setRecipientName(destinationDTO.getRecipientName());
        destination.setStatus(destinationDTO.getStatus());
        final Orders order = destinationDTO.getOrder() == null ? null : ordersRepository.findById(destinationDTO.getOrder())
                .orElseThrow(() -> new NotFoundException("order not found"));
        destination.setOrder(order);
        final Riders deliveryBy = destinationDTO.getDeliveryBy() == null ? null : ridersRepository.findById(destinationDTO.getDeliveryBy())
                .orElseThrow(() -> new NotFoundException("deliveryBy not found"));
        destination.setDeliveryBy(deliveryBy);
        return destination;
    }

    @Override
    protected DestinationDTO createDTO() {
        return new DestinationDTO();
    }

    @Override
    protected Destination createEntity() {
        return new Destination();
    }

    public ReferencedWarning getReferencedWarning(final Long destinationId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Destination destination = destinationRepository.findById(destinationId)
                .orElseThrow(NotFoundException::new);
        final Evidence destinationEvidence = evidenceRepository.findFirstByDestination(destination);
        if (destinationEvidence != null) {
            referencedWarning.setKey("destination.evidence.destination.referenced");
            referencedWarning.addParam(destinationEvidence.getEvidenceId());
            return referencedWarning;
        }
        final Item destinationItem = itemRepository.findFirstByDestination(destination);
        if (destinationItem != null) {
            referencedWarning.setKey("destination.item.destination.referenced");
            referencedWarning.addParam(destinationItem.getItemId());
            return referencedWarning;
        }
        final NoteDestination destinationNoteDestination = noteDestinationRepository.findFirstByDestination(destination);
        if (destinationNoteDestination != null) {
            referencedWarning.setKey("destination.noteDestination.destination.referenced");
            referencedWarning.addParam(destinationNoteDestination.getNoteDestinationId());
            return referencedWarning;
        }
        return null;
    }

}

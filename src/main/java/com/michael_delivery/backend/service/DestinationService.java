package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.*;
import com.michael_delivery.backend.model.DestinationDTO;
import com.michael_delivery.backend.repos.*;
import com.michael_delivery.backend.util.NotFoundException;
import com.michael_delivery.backend.util.ReferencedWarning;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DestinationService {

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
        this.destinationRepository = destinationRepository;
        this.ordersRepository = ordersRepository;
        this.ridersRepository = ridersRepository;
        this.evidenceRepository = evidenceRepository;
        this.itemRepository = itemRepository;
        this.noteDestinationRepository = noteDestinationRepository;
    }

    public List<DestinationDTO> findAll() {
        final List<Destination> destinations = destinationRepository.findAll(Sort.by("destinationId"));
        return destinations.stream()
                .map(destination -> mapToDTO(destination, new DestinationDTO()))
                .toList();
    }

    public DestinationDTO get(final Long destinationId) {
        return destinationRepository.findById(destinationId)
                .map(destination -> mapToDTO(destination, new DestinationDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final DestinationDTO destinationDTO) {
        final Destination destination = new Destination();
        mapToEntity(destinationDTO, destination);
        return destinationRepository.save(destination).getDestinationId();
    }

    public void update(final Long destinationId, final DestinationDTO destinationDTO) {
        final Destination destination = destinationRepository.findById(destinationId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(destinationDTO, destination);
        destinationRepository.save(destination);
    }

    public void delete(final Long destinationId) {
        destinationRepository.deleteById(destinationId);
    }

    private DestinationDTO mapToDTO(final Destination destination,
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

    private Destination mapToEntity(final DestinationDTO destinationDTO,
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

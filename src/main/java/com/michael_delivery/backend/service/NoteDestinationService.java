package com.michael_delivery.backend.service;

import com.michael_delivery.backend.model.Destination;
import com.michael_delivery.backend.model.NoteDestination;
import com.michael_delivery.backend.dto.NoteDestinationDTO;
import com.michael_delivery.backend.repository.DestinationRepository;
import com.michael_delivery.backend.repository.NoteDestinationRepository;
import com.michael_delivery.backend.util.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
public class NoteDestinationService extends BaseService<NoteDestination, NoteDestinationDTO,Long, NoteDestinationRepository>{

    private final NoteDestinationRepository noteDestinationRepository;
    private final DestinationRepository destinationRepository;

    public NoteDestinationService(final NoteDestinationRepository noteDestinationRepository,
            final DestinationRepository destinationRepository) {
        super(noteDestinationRepository,"noteDestinationId");

        this.noteDestinationRepository = noteDestinationRepository;
        this.destinationRepository = destinationRepository;
    }

    @Override
    public Page<NoteDestinationDTO> search(Specification<NoteDestination> query, Pageable pageable) {
        return this.noteDestinationRepository.findAll(query, pageable);
    }
    @Override
    protected NoteDestinationDTO mapToDTO(final NoteDestination noteDestination,
            final NoteDestinationDTO noteDestinationDTO) {
        noteDestinationDTO.setNoteDestinationId(noteDestination.getNoteDestinationId());
        noteDestinationDTO.setNote(noteDestination.getNote());
        noteDestinationDTO.setPhotoUrls(noteDestination.getPhotoUrls());
        noteDestinationDTO.setDestination(noteDestination.getDestination() == null ? null : noteDestination.getDestination().getDestinationId());
        return noteDestinationDTO;
    }

    @Override
    protected NoteDestination mapToEntity(final NoteDestinationDTO noteDestinationDTO,
            final NoteDestination noteDestination) {
        noteDestination.setNote(noteDestinationDTO.getNote());
        noteDestination.setPhotoUrls(noteDestinationDTO.getPhotoUrls());
        final Destination destination = noteDestinationDTO.getDestination() == null ? null : destinationRepository.findById(noteDestinationDTO.getDestination())
                .orElseThrow(() -> new NotFoundException("destination not found"));
        noteDestination.setDestination(destination);
        return noteDestination;
    }

    @Override
    protected NoteDestinationDTO createDTO() {
        return new NoteDestinationDTO();
    }

    @Override
    protected NoteDestination createEntity() {
        return new NoteDestination();
    }

}

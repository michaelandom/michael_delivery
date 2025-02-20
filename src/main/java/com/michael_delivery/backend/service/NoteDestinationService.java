package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.Destination;
import com.michael_delivery.backend.domain.NoteDestination;
import com.michael_delivery.backend.model.NoteDestinationDTO;
import com.michael_delivery.backend.repos.DestinationRepository;
import com.michael_delivery.backend.repos.NoteDestinationRepository;
import com.michael_delivery.backend.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class NoteDestinationService {

    private final NoteDestinationRepository noteDestinationRepository;
    private final DestinationRepository destinationRepository;

    public NoteDestinationService(final NoteDestinationRepository noteDestinationRepository,
            final DestinationRepository destinationRepository) {
        this.noteDestinationRepository = noteDestinationRepository;
        this.destinationRepository = destinationRepository;
    }

    public List<NoteDestinationDTO> findAll() {
        final List<NoteDestination> noteDestinations = noteDestinationRepository.findAll(Sort.by("noteDestinationId"));
        return noteDestinations.stream()
                .map(noteDestination -> mapToDTO(noteDestination, new NoteDestinationDTO()))
                .toList();
    }

    public NoteDestinationDTO get(final Long noteDestinationId) {
        return noteDestinationRepository.findById(noteDestinationId)
                .map(noteDestination -> mapToDTO(noteDestination, new NoteDestinationDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final NoteDestinationDTO noteDestinationDTO) {
        final NoteDestination noteDestination = new NoteDestination();
        mapToEntity(noteDestinationDTO, noteDestination);
        return noteDestinationRepository.save(noteDestination).getNoteDestinationId();
    }

    public void update(final Long noteDestinationId, final NoteDestinationDTO noteDestinationDTO) {
        final NoteDestination noteDestination = noteDestinationRepository.findById(noteDestinationId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(noteDestinationDTO, noteDestination);
        noteDestinationRepository.save(noteDestination);
    }

    public void delete(final Long noteDestinationId) {
        noteDestinationRepository.deleteById(noteDestinationId);
    }

    private NoteDestinationDTO mapToDTO(final NoteDestination noteDestination,
            final NoteDestinationDTO noteDestinationDTO) {
        noteDestinationDTO.setNoteDestinationId(noteDestination.getNoteDestinationId());
        noteDestinationDTO.setNote(noteDestination.getNote());
        noteDestinationDTO.setPhotoUrls(noteDestination.getPhotoUrls());
        noteDestinationDTO.setDestination(noteDestination.getDestination() == null ? null : noteDestination.getDestination().getDestinationId());
        return noteDestinationDTO;
    }

    private NoteDestination mapToEntity(final NoteDestinationDTO noteDestinationDTO,
            final NoteDestination noteDestination) {
        noteDestination.setNote(noteDestinationDTO.getNote());
        noteDestination.setPhotoUrls(noteDestinationDTO.getPhotoUrls());
        final Destination destination = noteDestinationDTO.getDestination() == null ? null : destinationRepository.findById(noteDestinationDTO.getDestination())
                .orElseThrow(() -> new NotFoundException("destination not found"));
        noteDestination.setDestination(destination);
        return noteDestination;
    }

}

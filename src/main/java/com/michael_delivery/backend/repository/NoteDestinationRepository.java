package com.michael_delivery.backend.repository;

import com.michael_delivery.backend.model.Destination;
import com.michael_delivery.backend.model.NoteDestination;
import com.michael_delivery.backend.dto.NoteDestinationDTO;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NoteDestinationRepository extends JpaRepository<NoteDestination, Long> ,BaseRepository<NoteDestinationDTO,NoteDestination> {

    NoteDestination findFirstByDestination(Destination destination);

}

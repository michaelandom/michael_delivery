package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.Destination;
import com.michael_delivery.backend.domain.NoteDestination;
import com.michael_delivery.backend.model.NoteDestinationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NoteDestinationRepository extends JpaRepository<NoteDestination, Long> ,BaseRepository<NoteDestinationDTO,NoteDestination> {

    NoteDestination findFirstByDestination(Destination destination);

}

package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.Destination;
import com.michael_delivery.backend.domain.NoteDestination;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NoteDestinationRepository extends JpaRepository<NoteDestination, Long> {

    NoteDestination findFirstByDestination(Destination destination);

}

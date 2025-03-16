package com.michael_delivery.backend.repository;

import com.michael_delivery.backend.model.Destination;
import com.michael_delivery.backend.model.Evidence;
import com.michael_delivery.backend.dto.EvidenceDTO;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EvidenceRepository extends JpaRepository<Evidence, Long>,BaseRepository<EvidenceDTO,Evidence> {

    Evidence findFirstByDestination(Destination destination);


}

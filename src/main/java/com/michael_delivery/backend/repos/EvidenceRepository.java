package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.Destination;
import com.michael_delivery.backend.domain.Evidence;
import com.michael_delivery.backend.model.EvidenceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EvidenceRepository extends JpaRepository<Evidence, Long>,BaseRepository<EvidenceDTO,Evidence> {

    Evidence findFirstByDestination(Destination destination);


}

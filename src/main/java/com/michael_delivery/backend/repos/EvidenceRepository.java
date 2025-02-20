package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.Destination;
import com.michael_delivery.backend.domain.Evidence;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EvidenceRepository extends JpaRepository<Evidence, Long> {

    Evidence findFirstByDestination(Destination destination);

}

package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.Reference;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReferenceRepository extends JpaRepository<Reference, Long> {
}

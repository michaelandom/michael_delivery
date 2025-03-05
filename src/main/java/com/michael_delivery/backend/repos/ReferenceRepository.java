package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.Reference;
import com.michael_delivery.backend.model.ReferenceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReferenceRepository extends JpaRepository<Reference, Long> {

    public Page<ReferenceDTO> findAll(Specification<Reference> spec, Pageable pageable);


}

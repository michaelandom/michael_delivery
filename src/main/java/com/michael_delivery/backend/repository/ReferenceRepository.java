package com.michael_delivery.backend.repository;

import com.michael_delivery.backend.model.Reference;
import com.michael_delivery.backend.dto.ReferenceDTO;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReferenceRepository extends JpaRepository<Reference, Long>  ,BaseRepository<ReferenceDTO,Reference> {



}

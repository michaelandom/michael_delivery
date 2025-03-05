package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.Permissions;
import com.michael_delivery.backend.model.PermissionsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PermissionsRepository extends JpaRepository<Permissions, Long> {
    public Page<PermissionsDTO> findAll(Specification<Permissions> spec, Pageable pageable);

}

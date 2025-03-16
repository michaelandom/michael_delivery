package com.michael_delivery.backend.repository;

import com.michael_delivery.backend.model.Permissions;
import com.michael_delivery.backend.dto.PermissionsDTO;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PermissionsRepository extends JpaRepository<Permissions, Long>  ,BaseRepository<PermissionsDTO,Permissions> {
}

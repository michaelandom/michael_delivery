package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.Permissions;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PermissionsRepository extends JpaRepository<Permissions, Long> {
}

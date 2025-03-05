package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.GroupPermissions;
import com.michael_delivery.backend.domain.Groups;
import com.michael_delivery.backend.domain.Permissions;
import com.michael_delivery.backend.model.GroupPermissionsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GroupPermissionsRepository extends JpaRepository<GroupPermissions, Long> {

    GroupPermissions findFirstByGroup(Groups groups);

    GroupPermissions findFirstByPermission(Permissions permissions);

    public Page<GroupPermissionsDTO> findAll(Specification<GroupPermissions> spec, Pageable pageable);

}

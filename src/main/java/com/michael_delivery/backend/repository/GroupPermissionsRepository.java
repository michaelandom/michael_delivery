package com.michael_delivery.backend.repository;

import com.michael_delivery.backend.model.GroupPermissions;
import com.michael_delivery.backend.model.Groups;
import com.michael_delivery.backend.model.Permissions;
import com.michael_delivery.backend.dto.GroupPermissionsDTO;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GroupPermissionsRepository extends JpaRepository<GroupPermissions, Long> ,BaseRepository<GroupPermissionsDTO,GroupPermissions> {

    GroupPermissions findFirstByGroup(Groups groups);

    GroupPermissions findFirstByPermission(Permissions permissions);

}

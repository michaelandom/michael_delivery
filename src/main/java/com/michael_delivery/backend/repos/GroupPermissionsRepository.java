package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.GroupPermissions;
import com.michael_delivery.backend.domain.Groups;
import com.michael_delivery.backend.domain.Permissions;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GroupPermissionsRepository extends JpaRepository<GroupPermissions, Long> {

    GroupPermissions findFirstByGroup(Groups groups);

    GroupPermissions findFirstByPermission(Permissions permissions);

}

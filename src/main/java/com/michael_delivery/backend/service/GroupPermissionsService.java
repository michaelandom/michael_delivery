package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.GroupPermissions;
import com.michael_delivery.backend.domain.Groups;
import com.michael_delivery.backend.domain.Permissions;
import com.michael_delivery.backend.model.GroupPermissionsDTO;
import com.michael_delivery.backend.repos.GroupPermissionsRepository;
import com.michael_delivery.backend.repos.GroupsRepository;
import com.michael_delivery.backend.repos.PermissionsRepository;
import com.michael_delivery.backend.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class GroupPermissionsService extends BaseService<GroupPermissions,GroupPermissionsDTO,Long,GroupPermissionsRepository> {

    private final GroupPermissionsRepository groupPermissionsRepository;
    private final GroupsRepository groupsRepository;
    private final PermissionsRepository permissionsRepository;

    public GroupPermissionsService(final GroupPermissionsRepository groupPermissionsRepository,
            final GroupsRepository groupsRepository,
            final PermissionsRepository permissionsRepository) {

        super(groupPermissionsRepository,"groupPermissionId");
        this.groupPermissionsRepository = groupPermissionsRepository;
        this.groupsRepository = groupsRepository;
        this.permissionsRepository = permissionsRepository;
    }

    @Override
    protected GroupPermissionsDTO mapToDTO(final GroupPermissions groupPermissions,
            final GroupPermissionsDTO groupPermissionsDTO) {
        groupPermissionsDTO.setGroupPermissionId(groupPermissions.getGroupPermissionId());
        groupPermissionsDTO.setGroup(groupPermissions.getGroup() == null ? null : groupPermissions.getGroup().getGroupId());
        groupPermissionsDTO.setPermission(groupPermissions.getPermission() == null ? null : groupPermissions.getPermission().getPermissionId());
        return groupPermissionsDTO;
    }

    @Override
    protected GroupPermissions mapToEntity(final GroupPermissionsDTO groupPermissionsDTO,
            final GroupPermissions groupPermissions) {
        final Groups group = groupPermissionsDTO.getGroup() == null ? null : groupsRepository.findById(groupPermissionsDTO.getGroup())
                .orElseThrow(() -> new NotFoundException("group not found"));
        groupPermissions.setGroup(group);
        final Permissions permission = groupPermissionsDTO.getPermission() == null ? null : permissionsRepository.findById(groupPermissionsDTO.getPermission())
                .orElseThrow(() -> new NotFoundException("permission not found"));
        groupPermissions.setPermission(permission);
        return groupPermissions;
    }

    @Override
    protected GroupPermissionsDTO createDTO() {
        return new GroupPermissionsDTO();
    }

    @Override
    protected GroupPermissions createEntity() {
        return new GroupPermissions();
    }

}

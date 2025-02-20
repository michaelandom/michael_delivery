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
public class GroupPermissionsService {

    private final GroupPermissionsRepository groupPermissionsRepository;
    private final GroupsRepository groupsRepository;
    private final PermissionsRepository permissionsRepository;

    public GroupPermissionsService(final GroupPermissionsRepository groupPermissionsRepository,
            final GroupsRepository groupsRepository,
            final PermissionsRepository permissionsRepository) {
        this.groupPermissionsRepository = groupPermissionsRepository;
        this.groupsRepository = groupsRepository;
        this.permissionsRepository = permissionsRepository;
    }

    public List<GroupPermissionsDTO> findAll() {
        final List<GroupPermissions> groupPermissionses = groupPermissionsRepository.findAll(Sort.by("groupPermissionId"));
        return groupPermissionses.stream()
                .map(groupPermissions -> mapToDTO(groupPermissions, new GroupPermissionsDTO()))
                .toList();
    }

    public GroupPermissionsDTO get(final Long groupPermissionId) {
        return groupPermissionsRepository.findById(groupPermissionId)
                .map(groupPermissions -> mapToDTO(groupPermissions, new GroupPermissionsDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final GroupPermissionsDTO groupPermissionsDTO) {
        final GroupPermissions groupPermissions = new GroupPermissions();
        mapToEntity(groupPermissionsDTO, groupPermissions);
        return groupPermissionsRepository.save(groupPermissions).getGroupPermissionId();
    }

    public void update(final Long groupPermissionId,
            final GroupPermissionsDTO groupPermissionsDTO) {
        final GroupPermissions groupPermissions = groupPermissionsRepository.findById(groupPermissionId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(groupPermissionsDTO, groupPermissions);
        groupPermissionsRepository.save(groupPermissions);
    }

    public void delete(final Long groupPermissionId) {
        groupPermissionsRepository.deleteById(groupPermissionId);
    }

    private GroupPermissionsDTO mapToDTO(final GroupPermissions groupPermissions,
            final GroupPermissionsDTO groupPermissionsDTO) {
        groupPermissionsDTO.setGroupPermissionId(groupPermissions.getGroupPermissionId());
        groupPermissionsDTO.setGroup(groupPermissions.getGroup() == null ? null : groupPermissions.getGroup().getGroupId());
        groupPermissionsDTO.setPermission(groupPermissions.getPermission() == null ? null : groupPermissions.getPermission().getPermissionId());
        return groupPermissionsDTO;
    }

    private GroupPermissions mapToEntity(final GroupPermissionsDTO groupPermissionsDTO,
            final GroupPermissions groupPermissions) {
        final Groups group = groupPermissionsDTO.getGroup() == null ? null : groupsRepository.findById(groupPermissionsDTO.getGroup())
                .orElseThrow(() -> new NotFoundException("group not found"));
        groupPermissions.setGroup(group);
        final Permissions permission = groupPermissionsDTO.getPermission() == null ? null : permissionsRepository.findById(groupPermissionsDTO.getPermission())
                .orElseThrow(() -> new NotFoundException("permission not found"));
        groupPermissions.setPermission(permission);
        return groupPermissions;
    }

}

package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.GroupPermissions;
import com.michael_delivery.backend.domain.Permissions;
import com.michael_delivery.backend.model.PermissionsDTO;
import com.michael_delivery.backend.repos.GroupPermissionsRepository;
import com.michael_delivery.backend.repos.PermissionsRepository;
import com.michael_delivery.backend.util.NotFoundException;
import com.michael_delivery.backend.util.ReferencedWarning;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PermissionsService {

    private final PermissionsRepository permissionsRepository;
    private final GroupPermissionsRepository groupPermissionsRepository;

    public PermissionsService(final PermissionsRepository permissionsRepository,
                              final GroupPermissionsRepository groupPermissionsRepository) {
        this.permissionsRepository = permissionsRepository;
        this.groupPermissionsRepository = groupPermissionsRepository;
    }

    public List<PermissionsDTO> findAll() {
        final List<Permissions> permissionses = permissionsRepository.findAll(Sort.by("permissionId"));
        return permissionses.stream()
                .map(permissions -> mapToDTO(permissions, new PermissionsDTO()))
                .toList();
    }

    public PermissionsDTO get(final Long permissionId) {
        return permissionsRepository.findById(permissionId)
                .map(permissions -> mapToDTO(permissions, new PermissionsDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final PermissionsDTO permissionsDTO) {
        final Permissions permissions = new Permissions();
        mapToEntity(permissionsDTO, permissions);
        return permissionsRepository.save(permissions).getPermissionId();
    }

    public void update(final Long permissionId, final PermissionsDTO permissionsDTO) {
        final Permissions permissions = permissionsRepository.findById(permissionId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(permissionsDTO, permissions);
        permissionsRepository.save(permissions);
    }

    public void delete(final Long permissionId) {
        permissionsRepository.deleteById(permissionId);
    }

    private PermissionsDTO mapToDTO(final Permissions permissions,
            final PermissionsDTO permissionsDTO) {
        permissionsDTO.setPermissionId(permissions.getPermissionId());
        permissionsDTO.setPermissionName(permissions.getPermissionName());
        permissionsDTO.setDescription(permissions.getDescription());
        return permissionsDTO;
    }

    private Permissions mapToEntity(final PermissionsDTO permissionsDTO,
            final Permissions permissions) {
        permissions.setPermissionName(permissionsDTO.getPermissionName());
        permissions.setDescription(permissionsDTO.getDescription());
        return permissions;
    }

    public ReferencedWarning getReferencedWarning(final Long permissionId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Permissions permissions = permissionsRepository.findById(permissionId)
                .orElseThrow(NotFoundException::new);
        final GroupPermissions permissionGroupPermissions = groupPermissionsRepository.findFirstByPermission(permissions);
        if (permissionGroupPermissions != null) {
            referencedWarning.setKey("permissions.groupPermissions.permission.referenced");
            referencedWarning.addParam(permissionGroupPermissions.getGroupPermissionId());
            return referencedWarning;
        }
        return null;
    }

}

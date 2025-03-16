package com.michael_delivery.backend.service;

import com.michael_delivery.backend.model.GroupPermissions;
import com.michael_delivery.backend.model.Permissions;
import com.michael_delivery.backend.dto.PermissionsDTO;
import com.michael_delivery.backend.repository.GroupPermissionsRepository;
import com.michael_delivery.backend.repository.PermissionsRepository;
import com.michael_delivery.backend.util.NotFoundException;
import com.michael_delivery.backend.util.ReferencedWarning;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
public class PermissionsService extends BaseService<Permissions, PermissionsDTO,Long, PermissionsRepository>{

    private final PermissionsRepository permissionsRepository;
    private final GroupPermissionsRepository groupPermissionsRepository;

    public PermissionsService(final PermissionsRepository permissionsRepository,
                              final GroupPermissionsRepository groupPermissionsRepository) {
        super(permissionsRepository,"permissionId");
        this.permissionsRepository = permissionsRepository;
        this.groupPermissionsRepository = groupPermissionsRepository;
    }


    @Override
    public Page<PermissionsDTO> search(Specification<Permissions> query, Pageable pageable) {
        return this.permissionsRepository.findAll(query, pageable);
    }

    @Override
    protected PermissionsDTO mapToDTO(final Permissions permissions,
            final PermissionsDTO permissionsDTO) {
        permissionsDTO.setPermissionId(permissions.getPermissionId());
        permissionsDTO.setPermissionName(permissions.getPermissionName());
        permissionsDTO.setDescription(permissions.getDescription());
        return permissionsDTO;
    }

    @Override
    protected Permissions mapToEntity(final PermissionsDTO permissionsDTO,
            final Permissions permissions) {
        permissions.setPermissionName(permissionsDTO.getPermissionName());
        permissions.setDescription(permissionsDTO.getDescription());
        return permissions;
    }

    @Override
    protected PermissionsDTO createDTO() {
        return new PermissionsDTO();
    }

    @Override
    protected Permissions createEntity() {
        return new Permissions();
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

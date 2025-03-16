package com.michael_delivery.backend.controller;

import com.michael_delivery.backend.model.Permissions;
import com.michael_delivery.backend.dto.PageableBodyDTO;
import com.michael_delivery.backend.dto.PermissionsDTO;
import com.michael_delivery.backend.specification.GenericSpecification;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.service.PermissionsService;
import com.michael_delivery.backend.util.ReferencedException;
import com.michael_delivery.backend.util.ReferencedWarning;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api/permissions", produces = MediaType.APPLICATION_JSON_VALUE)
public class PermissionsController {

    private final PermissionsService permissionsService;

    public PermissionsController(final PermissionsService permissionsService) {
        this.permissionsService = permissionsService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('MANAGE_PERMISSIONS','VIEW_PERMISSION_MANY')")
    public ResponseEntity<List<PermissionsDTO>> getAllPermissions(
    ) {
        return ResponseEntity.ok(permissionsService.findAll());
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('MANAGE_PERMISSIONS','VIEW_PERMISSION_MANY')")
    public ResponseEntity<Page<PermissionsDTO>> searchPermissions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "permissionId:asc") String[] sortBy,
            @RequestParam(required = false) String permissionName,
            @RequestParam(required = false) String description
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        GenericSpecification<Permissions> spec = new GenericSpecification<>();
        Specification<Permissions> permissionNameSpec = spec.contains("permissionName", permissionName);
        Specification<Permissions> descriptionSpec = spec.contains("description", description);
        Specification<Permissions> finalSpec = Specification.where(permissionNameSpec)
                .and(descriptionSpec);
        return ResponseEntity.ok(permissionsService.search(finalSpec,pageable.getPageable()));
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('MANAGE_PERMISSIONS','VIEW_PERMISSION_MANY')")
    public ResponseEntity<Page<PermissionsDTO>> getAllPermissions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "permissionId:asc") String[] sortBy
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        return ResponseEntity.ok(permissionsService.findAll(pageable.getPageable()));
    }

    @GetMapping("/{permissionId}")
    @PreAuthorize("hasAnyAuthority('MANAGE_PERMISSIONS','VIEW_PERMISSION')")
    public ResponseEntity<PermissionsDTO> getPermissions(
            @PathVariable(name = "permissionId") final Long permissionId) {
        return ResponseEntity.ok(permissionsService.get(permissionId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    @PreAuthorize("hasAnyAuthority('MANAGE_PERMISSIONS','UPDATE_PERMISSION_MANY')")
    public ResponseEntity<Long> createPermissions(
            @RequestBody @Valid final PermissionsDTO permissionsDTO) {
        final Long createdPermissionId = permissionsService.create(permissionsDTO);
        return new ResponseEntity<>(createdPermissionId, HttpStatus.CREATED);
    }

    @PutMapping("/{permissionId}")
    @PreAuthorize("hasAnyAuthority('MANAGE_PERMISSIONS','UPDATE_PERMISSION_MANY')")
    public ResponseEntity<Long> updatePermissions(
            @PathVariable(name = "permissionId") final Long permissionId,
            @RequestBody @Valid final PermissionsDTO permissionsDTO) {
        permissionsService.update(permissionId, permissionsDTO);
        return ResponseEntity.ok(permissionId);
    }

    @DeleteMapping("/{permissionId}")
    @ApiResponse(responseCode = "204")
    @PreAuthorize("hasAnyAuthority('MANAGE_PERMISSIONS','DELETE_PERMISSION_ONE')")
    public ResponseEntity<Void> deletePermissions(
            @PathVariable(name = "permissionId") final Long permissionId) {
        final ReferencedWarning referencedWarning = permissionsService.getReferencedWarning(permissionId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        permissionsService.delete(permissionId);
        return ResponseEntity.noContent().build();
    }

}

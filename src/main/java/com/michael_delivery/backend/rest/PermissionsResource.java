package com.michael_delivery.backend.rest;

import com.michael_delivery.backend.domain.Permissions;
import com.michael_delivery.backend.model.PageableBodyDTO;
import com.michael_delivery.backend.model.PermissionsDTO;
import com.michael_delivery.backend.specification.GenericSpecification;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.model.PermissionsDTO;
import com.michael_delivery.backend.service.PermissionsService;
import com.michael_delivery.backend.util.ReferencedException;
import com.michael_delivery.backend.util.ReferencedWarning;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api/permissions", produces = MediaType.APPLICATION_JSON_VALUE)
public class PermissionsResource {

    private final PermissionsService permissionsService;

    public PermissionsResource(final PermissionsService permissionsService) {
        this.permissionsService = permissionsService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<PermissionsDTO>> getAllPermissions(
    ) {
        return ResponseEntity.ok(permissionsService.findAll());
    }

    @GetMapping("/search")
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
    public ResponseEntity<Page<PermissionsDTO>> getAllPermissions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "permissionId:asc") String[] sortBy
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        return ResponseEntity.ok(permissionsService.findAll(pageable.getPageable()));
    }

    @GetMapping("/{permissionId}")
    public ResponseEntity<PermissionsDTO> getPermissions(
            @PathVariable(name = "permissionId") final Long permissionId) {
        return ResponseEntity.ok(permissionsService.get(permissionId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createPermissions(
            @RequestBody @Valid final PermissionsDTO permissionsDTO) {
        final Long createdPermissionId = permissionsService.create(permissionsDTO);
        return new ResponseEntity<>(createdPermissionId, HttpStatus.CREATED);
    }

    @PutMapping("/{permissionId}")
    public ResponseEntity<Long> updatePermissions(
            @PathVariable(name = "permissionId") final Long permissionId,
            @RequestBody @Valid final PermissionsDTO permissionsDTO) {
        permissionsService.update(permissionId, permissionsDTO);
        return ResponseEntity.ok(permissionId);
    }

    @DeleteMapping("/{permissionId}")
    @ApiResponse(responseCode = "204")
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

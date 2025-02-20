package com.michael_delivery.backend.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.model.PermissionsDTO;
import com.michael_delivery.backend.service.PermissionsService;
import com.michael_delivery.backend.util.ReferencedException;
import com.michael_delivery.backend.util.ReferencedWarning;
import jakarta.validation.Valid;
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

    @GetMapping
    public ResponseEntity<List<PermissionsDTO>> getAllPermissions() {
        return ResponseEntity.ok(permissionsService.findAll());
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

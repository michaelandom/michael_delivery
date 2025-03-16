package com.michael_delivery.backend.controller;

import com.michael_delivery.backend.dto.GroupPermissionsDTO;
import com.michael_delivery.backend.dto.PageableBodyDTO;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.model.Groups;
import com.michael_delivery.backend.model.Permissions;
import com.michael_delivery.backend.repository.GroupsRepository;
import com.michael_delivery.backend.repository.PermissionsRepository;
import com.michael_delivery.backend.service.GroupPermissionsService;
import com.michael_delivery.backend.util.CustomCollectors;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/api/groupPermissions", produces = MediaType.APPLICATION_JSON_VALUE)
public class GroupPermissionsController {

    private final GroupPermissionsService groupPermissionsService;
    private final GroupsRepository groupsRepository;
    private final PermissionsRepository permissionsRepository;

    public GroupPermissionsController(final GroupPermissionsService groupPermissionsService,
            final GroupsRepository groupsRepository,
            final PermissionsRepository permissionsRepository) {
        this.groupPermissionsService = groupPermissionsService;
        this.groupsRepository = groupsRepository;
        this.permissionsRepository = permissionsRepository;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('MANAGE_GROUP_PERMISSIONS','VIEW_GROUP_PERMISSION_MANY')")
    public ResponseEntity<List<GroupPermissionsDTO>> getAllGroupPermissions(
    ) {
        return ResponseEntity.ok(groupPermissionsService.findAll());
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('MANAGE_GROUP_PERMISSIONS','VIEW_GROUP_PERMISSION_MANY')")
    public ResponseEntity<Page<GroupPermissionsDTO>> getAllGroupPermissions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "groupPermissionId:asc") String[] sortBy
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        return ResponseEntity.ok(groupPermissionsService.findAll(pageable.getPageable()));
    }

    @GetMapping("/{groupPermissionId}")
    @PreAuthorize("hasAnyAuthority('MANAGE_GROUP_PERMISSIONS','VIEW_GROUP_PERMISSION')")
    public ResponseEntity<GroupPermissionsDTO> getGroupPermissions(
            @PathVariable(name = "groupPermissionId") final Long groupPermissionId) {
        return ResponseEntity.ok(groupPermissionsService.get(groupPermissionId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    @PreAuthorize("hasAnyAuthority('MANAGE_GROUP_PERMISSIONS','UPDATE_GROUP_PERMISSION_ONE')")
    public ResponseEntity<Long> createGroupPermissions(
            @RequestBody @Valid final GroupPermissionsDTO groupPermissionsDTO) {
        final Long createdGroupPermissionId = groupPermissionsService.create(groupPermissionsDTO);
        return new ResponseEntity<>(createdGroupPermissionId, HttpStatus.CREATED);
    }

    @PutMapping("/{groupPermissionId}")
    @PreAuthorize("hasAnyAuthority('MANAGE_GROUP_PERMISSIONS','UPDATE_GROUP_PERMISSION_ONE')")
    public ResponseEntity<Long> updateGroupPermissions(
            @PathVariable(name = "groupPermissionId") final Long groupPermissionId,
            @RequestBody @Valid final GroupPermissionsDTO groupPermissionsDTO) {
        groupPermissionsService.update(groupPermissionId, groupPermissionsDTO);
        return ResponseEntity.ok(groupPermissionId);
    }

    @DeleteMapping("/{groupPermissionId}")
    @ApiResponse(responseCode = "204")
    @PreAuthorize("hasAnyAuthority('MANAGE_GROUP_PERMISSIONS','DELETE_GROUP_PERMISSION_ONE')")
    public ResponseEntity<Void> deleteGroupPermissions(
            @PathVariable(name = "groupPermissionId") final Long groupPermissionId) {
        groupPermissionsService.delete(groupPermissionId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/groupValues")
    @PreAuthorize("hasAnyAuthority('MANAGE_GROUP_PERMISSIONS','VIEW_GROUP_PERMISSION_MANY')")
    public ResponseEntity<Map<Long, String>> getGroupValues() {
        return ResponseEntity.ok(groupsRepository.findAll(Sort.by("groupId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Groups::getGroupId, Groups::getName)));
    }

    @GetMapping("/permissionValues")
    @PreAuthorize("hasAnyAuthority('MANAGE_GROUP_PERMISSIONS','VIEW_GROUP_PERMISSION_MANY')")
    public ResponseEntity<Map<Long, String>> getPermissionValues() {
        return ResponseEntity.ok(permissionsRepository.findAll(Sort.by("permissionId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Permissions::getPermissionId, Permissions::getPermissionName)));
    }

}

package com.michael_delivery.backend.rest;

import com.michael_delivery.backend.domain.GroupPermissions;
import com.michael_delivery.backend.model.GroupPermissionsDTO;
import com.michael_delivery.backend.model.PageableBodyDTO;
import com.michael_delivery.backend.specification.GenericSpecification;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.domain.Groups;
import com.michael_delivery.backend.domain.Permissions;
import com.michael_delivery.backend.model.GroupPermissionsDTO;
import com.michael_delivery.backend.repos.GroupsRepository;
import com.michael_delivery.backend.repos.PermissionsRepository;
import com.michael_delivery.backend.service.GroupPermissionsService;
import com.michael_delivery.backend.util.CustomCollectors;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/api/groupPermissions", produces = MediaType.APPLICATION_JSON_VALUE)
public class GroupPermissionsResource {

    private final GroupPermissionsService groupPermissionsService;
    private final GroupsRepository groupsRepository;
    private final PermissionsRepository permissionsRepository;

    public GroupPermissionsResource(final GroupPermissionsService groupPermissionsService,
            final GroupsRepository groupsRepository,
            final PermissionsRepository permissionsRepository) {
        this.groupPermissionsService = groupPermissionsService;
        this.groupsRepository = groupsRepository;
        this.permissionsRepository = permissionsRepository;
    }

    @GetMapping("/all")
    public ResponseEntity<List<GroupPermissionsDTO>> getAllGroupPermissions(
    ) {
        return ResponseEntity.ok(groupPermissionsService.findAll());
    }

    @GetMapping
    public ResponseEntity<Page<GroupPermissionsDTO>> getAllGroupPermissions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "groupPermissionId:asc") String[] sortBy
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        return ResponseEntity.ok(groupPermissionsService.findAll(pageable.getPageable()));
    }

    @GetMapping("/{groupPermissionId}")
    public ResponseEntity<GroupPermissionsDTO> getGroupPermissions(
            @PathVariable(name = "groupPermissionId") final Long groupPermissionId) {
        return ResponseEntity.ok(groupPermissionsService.get(groupPermissionId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createGroupPermissions(
            @RequestBody @Valid final GroupPermissionsDTO groupPermissionsDTO) {
        final Long createdGroupPermissionId = groupPermissionsService.create(groupPermissionsDTO);
        return new ResponseEntity<>(createdGroupPermissionId, HttpStatus.CREATED);
    }

    @PutMapping("/{groupPermissionId}")
    public ResponseEntity<Long> updateGroupPermissions(
            @PathVariable(name = "groupPermissionId") final Long groupPermissionId,
            @RequestBody @Valid final GroupPermissionsDTO groupPermissionsDTO) {
        groupPermissionsService.update(groupPermissionId, groupPermissionsDTO);
        return ResponseEntity.ok(groupPermissionId);
    }

    @DeleteMapping("/{groupPermissionId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteGroupPermissions(
            @PathVariable(name = "groupPermissionId") final Long groupPermissionId) {
        groupPermissionsService.delete(groupPermissionId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/groupValues")
    public ResponseEntity<Map<Long, String>> getGroupValues() {
        return ResponseEntity.ok(groupsRepository.findAll(Sort.by("groupId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Groups::getGroupId, Groups::getName)));
    }

    @GetMapping("/permissionValues")
    public ResponseEntity<Map<Long, String>> getPermissionValues() {
        return ResponseEntity.ok(permissionsRepository.findAll(Sort.by("permissionId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Permissions::getPermissionId, Permissions::getPermissionName)));
    }

}

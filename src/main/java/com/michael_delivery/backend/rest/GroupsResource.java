package com.michael_delivery.backend.rest;

import com.michael_delivery.backend.domain.Groups;
import com.michael_delivery.backend.model.GroupsDTO;
import com.michael_delivery.backend.model.PageableBodyDTO;
import com.michael_delivery.backend.specification.GenericSpecification;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.model.GroupsDTO;
import com.michael_delivery.backend.service.GroupsService;
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
@RequestMapping(value = "/api/groups", produces = MediaType.APPLICATION_JSON_VALUE)
public class GroupsResource {

    private final GroupsService groupsService;

    public GroupsResource(final GroupsService groupsService) {
        this.groupsService = groupsService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<GroupsDTO>> getAllGroups(
    ) {
        return ResponseEntity.ok(groupsService.findAll());
    }

    @GetMapping("/search")
    public ResponseEntity<Page<GroupsDTO>> searchGroups(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "groupId:asc") String[] sortBy,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        GenericSpecification<Groups> spec = new GenericSpecification<>();
        Specification<Groups> nameSpec = spec.contains("name", name);
        Specification<Groups> descriptionSpec = spec.contains("description", description);
        Specification<Groups> finalSpec = Specification.where(nameSpec).and(descriptionSpec);
        return ResponseEntity.ok(groupsService.search(finalSpec,pageable.getPageable()));
    }

    @GetMapping
    public ResponseEntity<Page<GroupsDTO>> getAllGroups(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "groupId:asc") String[] sortBy
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        return ResponseEntity.ok(groupsService.findAll(pageable.getPageable()));
    }
    @GetMapping("/{groupId}")
    public ResponseEntity<GroupsDTO> getGroups(@PathVariable(name = "groupId") final Long groupId) {
        return ResponseEntity.ok(groupsService.get(groupId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createGroups(@RequestBody @Valid final GroupsDTO groupsDTO) {
        final Long createdGroupId = groupsService.create(groupsDTO);
        return new ResponseEntity<>(createdGroupId, HttpStatus.CREATED);
    }

    @PutMapping("/{groupId}")
    public ResponseEntity<Long> updateGroups(@PathVariable(name = "groupId") final Long groupId,
            @RequestBody @Valid final GroupsDTO groupsDTO) {
        groupsService.update(groupId, groupsDTO);
        return ResponseEntity.ok(groupId);
    }

    @DeleteMapping("/{groupId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteGroups(@PathVariable(name = "groupId") final Long groupId) {
        final ReferencedWarning referencedWarning = groupsService.getReferencedWarning(groupId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        groupsService.delete(groupId);
        return ResponseEntity.noContent().build();
    }

}

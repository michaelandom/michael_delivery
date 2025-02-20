package com.michael_delivery.backend.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.model.GroupsDTO;
import com.michael_delivery.backend.service.GroupsService;
import com.michael_delivery.backend.util.ReferencedException;
import com.michael_delivery.backend.util.ReferencedWarning;
import jakarta.validation.Valid;
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

    @GetMapping
    public ResponseEntity<List<GroupsDTO>> getAllGroupss() {
        return ResponseEntity.ok(groupsService.findAll());
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

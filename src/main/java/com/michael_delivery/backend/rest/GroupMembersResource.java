package com.michael_delivery.backend.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.domain.Groups;
import com.michael_delivery.backend.domain.Users;
import com.michael_delivery.backend.model.GroupMembersDTO;
import com.michael_delivery.backend.repos.GroupsRepository;
import com.michael_delivery.backend.repos.UsersRepository;
import com.michael_delivery.backend.service.GroupMembersService;
import com.michael_delivery.backend.util.CustomCollectors;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/api/groupMembers", produces = MediaType.APPLICATION_JSON_VALUE)
public class GroupMembersResource {

    private final GroupMembersService groupMembersService;
    private final GroupsRepository groupsRepository;
    private final UsersRepository usersRepository;

    public GroupMembersResource(final GroupMembersService groupMembersService,
            final GroupsRepository groupsRepository, final UsersRepository usersRepository) {
        this.groupMembersService = groupMembersService;
        this.groupsRepository = groupsRepository;
        this.usersRepository = usersRepository;
    }

    @GetMapping
    public ResponseEntity<List<GroupMembersDTO>> getAllGroupMemberss() {
        return ResponseEntity.ok(groupMembersService.findAll());
    }

    @GetMapping("/{groupMemberId}")
    public ResponseEntity<GroupMembersDTO> getGroupMembers(
            @PathVariable(name = "groupMemberId") final Long groupMemberId) {
        return ResponseEntity.ok(groupMembersService.get(groupMemberId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createGroupMembers(
            @RequestBody @Valid final GroupMembersDTO groupMembersDTO) {
        final Long createdGroupMemberId = groupMembersService.create(groupMembersDTO);
        return new ResponseEntity<>(createdGroupMemberId, HttpStatus.CREATED);
    }

    @PutMapping("/{groupMemberId}")
    public ResponseEntity<Long> updateGroupMembers(
            @PathVariable(name = "groupMemberId") final Long groupMemberId,
            @RequestBody @Valid final GroupMembersDTO groupMembersDTO) {
        groupMembersService.update(groupMemberId, groupMembersDTO);
        return ResponseEntity.ok(groupMemberId);
    }

    @DeleteMapping("/{groupMemberId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteGroupMembers(
            @PathVariable(name = "groupMemberId") final Long groupMemberId) {
        groupMembersService.delete(groupMemberId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/groupValues")
    public ResponseEntity<Map<Long, String>> getGroupValues() {
        return ResponseEntity.ok(groupsRepository.findAll(Sort.by("groupId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Groups::getGroupId, Groups::getName)));
    }

    @GetMapping("/userValues")
    public ResponseEntity<Map<Long, String>> getUserValues() {
        return ResponseEntity.ok(usersRepository.findAll(Sort.by("userId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Users::getUserId, Users::getUsername)));
    }

}

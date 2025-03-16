package com.michael_delivery.backend.controller;

import com.michael_delivery.backend.dto.GroupMembersDTO;
import com.michael_delivery.backend.dto.PageableBodyDTO;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.model.Groups;
import com.michael_delivery.backend.model.Users;
import com.michael_delivery.backend.repository.GroupsRepository;
import com.michael_delivery.backend.repository.UsersRepository;
import com.michael_delivery.backend.service.GroupMembersService;
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
@RequestMapping(value = "/api/groupMembers", produces = MediaType.APPLICATION_JSON_VALUE)
public class GroupMembersController {

    private final GroupMembersService groupMembersService;
    private final GroupsRepository groupsRepository;
    private final UsersRepository usersRepository;

    public GroupMembersController(final GroupMembersService groupMembersService,
            final GroupsRepository groupsRepository, final UsersRepository usersRepository) {
        this.groupMembersService = groupMembersService;
        this.groupsRepository = groupsRepository;
        this.usersRepository = usersRepository;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('MANAGE_GROUP_MEMBERS','VIEW_GROUP_MEMBER_MANY')")
    public ResponseEntity<List<GroupMembersDTO>> getAllGroupMembers(
    ) {
        return ResponseEntity.ok(groupMembersService.findAll());
    }


    @GetMapping
    @PreAuthorize("hasAnyAuthority('MANAGE_GROUP_MEMBERS','VIEW_GROUP_MEMBER_MANY')")
    public ResponseEntity<Page<GroupMembersDTO>> getAllGroupMembers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "groupMemberId:asc") String[] sortBy
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        return ResponseEntity.ok(groupMembersService.findAll(pageable.getPageable()));
    }

    @GetMapping("/{groupMemberId}")
    @PreAuthorize("hasAnyAuthority('MANAGE_GROUP_MEMBERS','VIEW_GROUP_MEMBER')")
    public ResponseEntity<GroupMembersDTO> getGroupMembers(
            @PathVariable(name = "groupMemberId") final Long groupMemberId) {
        return ResponseEntity.ok(groupMembersService.get(groupMemberId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    @PreAuthorize("hasAnyAuthority('MANAGE_GROUP_MEMBERS','UPDATE_GROUP_MEMBER_ONE')")
    public ResponseEntity<Long> createGroupMembers(
            @RequestBody @Valid final GroupMembersDTO groupMembersDTO) {
        final Long createdGroupMemberId = groupMembersService.create(groupMembersDTO);
        return new ResponseEntity<>(createdGroupMemberId, HttpStatus.CREATED);
    }

    @PutMapping("/{groupMemberId}")
    @PreAuthorize("hasAnyAuthority('MANAGE_GROUP_MEMBERS','UPDATE_GROUP_MEMBER_ONE')")
    public ResponseEntity<Long> updateGroupMembers(
            @PathVariable(name = "groupMemberId") final Long groupMemberId,
            @RequestBody @Valid final GroupMembersDTO groupMembersDTO) {
        groupMembersService.update(groupMemberId, groupMembersDTO);
        return ResponseEntity.ok(groupMemberId);
    }

    @DeleteMapping("/{groupMemberId}")
    @ApiResponse(responseCode = "204")
    @PreAuthorize("hasAnyAuthority('MANAGE_GROUP_MEMBERS','DELETE_GROUP_MEMBER_ONE')")
    public ResponseEntity<Void> deleteGroupMembers(
            @PathVariable(name = "groupMemberId") final Long groupMemberId) {
        groupMembersService.delete(groupMemberId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/groupValues")
    @PreAuthorize("hasAnyAuthority('MANAGE_GROUP_MEMBERS','VIEW_GROUP_MEMBER_MANY')")
    public ResponseEntity<Map<Long, String>> getGroupValues() {
        return ResponseEntity.ok(groupsRepository.findAll(Sort.by("groupId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Groups::getGroupId, Groups::getName)));
    }

    @GetMapping("/userValues")
    @PreAuthorize("hasAnyAuthority('MANAGE_GROUP_MEMBERS','VIEW_GROUP_MEMBER_MANY')")
    public ResponseEntity<Map<Long, String>> getUserValues() {
        return ResponseEntity.ok(usersRepository.findAll(Sort.by("userId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Users::getUserId, Users::getUsername)));
    }

}

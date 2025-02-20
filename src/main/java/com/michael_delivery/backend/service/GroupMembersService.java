package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.GroupMembers;
import com.michael_delivery.backend.domain.Groups;
import com.michael_delivery.backend.domain.Users;
import com.michael_delivery.backend.model.GroupMembersDTO;
import com.michael_delivery.backend.repos.GroupMembersRepository;
import com.michael_delivery.backend.repos.GroupsRepository;
import com.michael_delivery.backend.repos.UsersRepository;
import com.michael_delivery.backend.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class GroupMembersService {

    private final GroupMembersRepository groupMembersRepository;
    private final GroupsRepository groupsRepository;
    private final UsersRepository usersRepository;

    public GroupMembersService(final GroupMembersRepository groupMembersRepository,
            final GroupsRepository groupsRepository, final UsersRepository usersRepository) {
        this.groupMembersRepository = groupMembersRepository;
        this.groupsRepository = groupsRepository;
        this.usersRepository = usersRepository;
    }

    public List<GroupMembersDTO> findAll() {
        final List<GroupMembers> groupMemberses = groupMembersRepository.findAll(Sort.by("groupMemberId"));
        return groupMemberses.stream()
                .map(groupMembers -> mapToDTO(groupMembers, new GroupMembersDTO()))
                .toList();
    }

    public GroupMembersDTO get(final Long groupMemberId) {
        return groupMembersRepository.findById(groupMemberId)
                .map(groupMembers -> mapToDTO(groupMembers, new GroupMembersDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final GroupMembersDTO groupMembersDTO) {
        final GroupMembers groupMembers = new GroupMembers();
        mapToEntity(groupMembersDTO, groupMembers);
        return groupMembersRepository.save(groupMembers).getGroupMemberId();
    }

    public void update(final Long groupMemberId, final GroupMembersDTO groupMembersDTO) {
        final GroupMembers groupMembers = groupMembersRepository.findById(groupMemberId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(groupMembersDTO, groupMembers);
        groupMembersRepository.save(groupMembers);
    }

    public void delete(final Long groupMemberId) {
        groupMembersRepository.deleteById(groupMemberId);
    }

    private GroupMembersDTO mapToDTO(final GroupMembers groupMembers,
            final GroupMembersDTO groupMembersDTO) {
        groupMembersDTO.setGroupMemberId(groupMembers.getGroupMemberId());
        groupMembersDTO.setGroup(groupMembers.getGroup() == null ? null : groupMembers.getGroup().getGroupId());
        groupMembersDTO.setUser(groupMembers.getUser() == null ? null : groupMembers.getUser().getUserId());
        return groupMembersDTO;
    }

    private GroupMembers mapToEntity(final GroupMembersDTO groupMembersDTO,
            final GroupMembers groupMembers) {
        final Groups group = groupMembersDTO.getGroup() == null ? null : groupsRepository.findById(groupMembersDTO.getGroup())
                .orElseThrow(() -> new NotFoundException("group not found"));
        groupMembers.setGroup(group);
        final Users user = groupMembersDTO.getUser() == null ? null : usersRepository.findById(groupMembersDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        groupMembers.setUser(user);
        return groupMembers;
    }

}

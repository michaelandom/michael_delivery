package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.*;
import com.michael_delivery.backend.model.DestinationDTO;
import com.michael_delivery.backend.model.FaqDTO;
import com.michael_delivery.backend.model.GroupMembersDTO;
import com.michael_delivery.backend.repos.FaqRepository;
import com.michael_delivery.backend.repos.GroupMembersRepository;
import com.michael_delivery.backend.repos.GroupsRepository;
import com.michael_delivery.backend.repos.UsersRepository;
import com.michael_delivery.backend.util.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class GroupMembersService extends BaseService<GroupMembers, GroupMembersDTO,Long, GroupMembersRepository>{

    private final GroupMembersRepository groupMembersRepository;
    private final GroupsRepository groupsRepository;
    private final UsersRepository usersRepository;

    public GroupMembersService(final GroupMembersRepository groupMembersRepository,
            final GroupsRepository groupsRepository, final UsersRepository usersRepository) {
        super(groupMembersRepository,"groupMemberId");
        this.groupMembersRepository = groupMembersRepository;
        this.groupsRepository = groupsRepository;
        this.usersRepository = usersRepository;
    }


    @Override
    public Page<GroupMembersDTO> search(Specification<GroupMembers> query, Pageable pageable) {
        return this.groupMembersRepository.findAll(query, pageable);
    }
    @Override
    protected GroupMembersDTO mapToDTO(final GroupMembers groupMembers,
            final GroupMembersDTO groupMembersDTO) {
        groupMembersDTO.setGroupMemberId(groupMembers.getGroupMemberId());
        groupMembersDTO.setGroup(groupMembers.getGroup() == null ? null : groupMembers.getGroup().getGroupId());
        groupMembersDTO.setUser(groupMembers.getUser() == null ? null : groupMembers.getUser().getUserId());
        return groupMembersDTO;
    }

    @Override
    protected GroupMembers mapToEntity(final GroupMembersDTO groupMembersDTO,
            final GroupMembers groupMembers) {
        final Groups group = groupMembersDTO.getGroup() == null ? null : groupsRepository.findById(groupMembersDTO.getGroup())
                .orElseThrow(() -> new NotFoundException("group not found"));
        groupMembers.setGroup(group);
        final Users user = groupMembersDTO.getUser() == null ? null : usersRepository.findById(groupMembersDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        groupMembers.setUser(user);
        return groupMembers;
    }

    @Override
    protected GroupMembersDTO createDTO() {
        return new GroupMembersDTO();
    }

    @Override
    protected GroupMembers createEntity() {
        return new GroupMembers();
    }

}

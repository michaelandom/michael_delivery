package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.EventGroups;
import com.michael_delivery.backend.domain.GroupMembers;
import com.michael_delivery.backend.domain.GroupPermissions;
import com.michael_delivery.backend.domain.Groups;
import com.michael_delivery.backend.model.GroupPermissionsDTO;
import com.michael_delivery.backend.model.GroupsDTO;
import com.michael_delivery.backend.repos.EventGroupsRepository;
import com.michael_delivery.backend.repos.GroupMembersRepository;
import com.michael_delivery.backend.repos.GroupPermissionsRepository;
import com.michael_delivery.backend.repos.GroupsRepository;
import com.michael_delivery.backend.util.NotFoundException;
import com.michael_delivery.backend.util.ReferencedWarning;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class GroupsService extends BaseService<Groups, GroupsDTO,Long,GroupsRepository> {

    private final GroupsRepository groupsRepository;
    private final GroupMembersRepository groupMembersRepository;
    private final GroupPermissionsRepository groupPermissionsRepository;
    private final EventGroupsRepository eventGroupsRepository;

    public GroupsService(final GroupsRepository groupsRepository,
            final GroupMembersRepository groupMembersRepository,
            final GroupPermissionsRepository groupPermissionsRepository,
            final EventGroupsRepository eventGroupsRepository) {
        super(groupsRepository,"groupId");
        this.groupsRepository = groupsRepository;
        this.groupMembersRepository = groupMembersRepository;
        this.groupPermissionsRepository = groupPermissionsRepository;
        this.eventGroupsRepository = eventGroupsRepository;
    }




    protected GroupsDTO mapToDTO(final Groups groups, final GroupsDTO groupsDTO) {
        groupsDTO.setGroupId(groups.getGroupId());
        groupsDTO.setName(groups.getName());
        groupsDTO.setDescription(groups.getDescription());
        groupsDTO.setGroupType(groups.getGroupType());
        return groupsDTO;
    }

    protected Groups mapToEntity(final GroupsDTO groupsDTO, final Groups groups) {
        groups.setName(groupsDTO.getName());
        groups.setDescription(groupsDTO.getDescription());
        groups.setGroupType(groupsDTO.getGroupType());
        return groups;
    }

    @Override
    protected GroupsDTO createDTO() {
        return new GroupsDTO();
    }

    @Override
    protected Groups createEntity() {
        return new Groups();
    }

    public ReferencedWarning getReferencedWarning(final Long groupId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Groups groups = groupsRepository.findById(groupId)
                .orElseThrow(NotFoundException::new);
        final GroupMembers groupGroupMembers = groupMembersRepository.findFirstByGroup(groups);
        if (groupGroupMembers != null) {
            referencedWarning.setKey("groups.groupMembers.group.referenced");
            referencedWarning.addParam(groupGroupMembers.getGroupMemberId());
            return referencedWarning;
        }
        final GroupPermissions groupGroupPermissions = groupPermissionsRepository.findFirstByGroup(groups);
        if (groupGroupPermissions != null) {
            referencedWarning.setKey("groups.groupPermissions.group.referenced");
            referencedWarning.addParam(groupGroupPermissions.getGroupPermissionId());
            return referencedWarning;
        }
        final EventGroups groupEventGroups = eventGroupsRepository.findFirstByGroup(groups);
        if (groupEventGroups != null) {
            referencedWarning.setKey("groups.eventGroups.group.referenced");
            referencedWarning.addParam(groupEventGroups.getEventGroupId());
            return referencedWarning;
        }
        return null;
    }

}

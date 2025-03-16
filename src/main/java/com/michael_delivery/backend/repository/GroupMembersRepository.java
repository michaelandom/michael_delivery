package com.michael_delivery.backend.repository;

import com.michael_delivery.backend.model.GroupMembers;
import com.michael_delivery.backend.model.Groups;
import com.michael_delivery.backend.model.Users;
import com.michael_delivery.backend.dto.GroupMembersDTO;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GroupMembersRepository extends JpaRepository<GroupMembers, Long>,BaseRepository<GroupMembersDTO,GroupMembers> {

    GroupMembers findFirstByGroup(Groups groups);

    GroupMembers findFirstByUser(Users users);

}

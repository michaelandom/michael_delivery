package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.GroupMembers;
import com.michael_delivery.backend.domain.Groups;
import com.michael_delivery.backend.domain.Users;
import com.michael_delivery.backend.model.GroupMembersDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GroupMembersRepository extends JpaRepository<GroupMembers, Long>,BaseRepository<GroupMembersDTO,GroupMembers> {

    GroupMembers findFirstByGroup(Groups groups);

    GroupMembers findFirstByUser(Users users);

}

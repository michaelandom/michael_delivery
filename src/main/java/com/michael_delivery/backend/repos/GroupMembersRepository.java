package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.GroupMembers;
import com.michael_delivery.backend.domain.Groups;
import com.michael_delivery.backend.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GroupMembersRepository extends JpaRepository<GroupMembers, Long> {

    GroupMembers findFirstByGroup(Groups groups);

    GroupMembers findFirstByUser(Users users);

}

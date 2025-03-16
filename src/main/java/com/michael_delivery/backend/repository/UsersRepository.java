package com.michael_delivery.backend.repository;

import com.michael_delivery.backend.model.BussinessAccount;
import com.michael_delivery.backend.model.SsoProvider;
import com.michael_delivery.backend.model.Users;
import com.michael_delivery.backend.dto.UsersDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;


public interface UsersRepository extends JpaRepository<Users, Long>  ,BaseRepository<UsersDTO,Users> {

    Users findFirstBySsoProvider(SsoProvider ssoProvider);

    Users findFirstByBussinessAccount(BussinessAccount bussinessAccount);
    Optional<Users> findByUsername(String username);

    @Query("SELECT p.permissionName\n" +
            "FROM Permissions p\n" +
            "JOIN GroupPermissions gp ON p.permissionId = gp.permission.permissionId\n" +
            "JOIN Groups g ON gp.group.groupId = g.groupId\n" +
            "JOIN GroupMembers gm ON g.groupId = gm.group.groupId\n" +
            "WHERE gm.user.userId = :userId")
    Set<String> findPermissionsById(Long userId);

}

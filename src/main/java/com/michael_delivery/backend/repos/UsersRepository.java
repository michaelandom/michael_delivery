package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.BillingAddress;
import com.michael_delivery.backend.domain.BussinessAccount;
import com.michael_delivery.backend.domain.SsoProvider;
import com.michael_delivery.backend.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;


public interface UsersRepository extends JpaRepository<Users, Long> {

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

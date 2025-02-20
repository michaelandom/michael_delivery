package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.PasswordReset;
import com.michael_delivery.backend.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PasswordResetRepository extends JpaRepository<PasswordReset, Long> {

    PasswordReset findFirstByUser(Users users);

    PasswordReset findFirstByReseatedBy(Users users);

}

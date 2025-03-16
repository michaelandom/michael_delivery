package com.michael_delivery.backend.repository;

import com.michael_delivery.backend.model.PasswordReset;
import com.michael_delivery.backend.model.Users;
import com.michael_delivery.backend.dto.PasswordResetDTO;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PasswordResetRepository extends JpaRepository<PasswordReset, Long> ,BaseRepository<PasswordResetDTO,PasswordReset> {

    PasswordReset findFirstByUser(Users users);

    PasswordReset findFirstByReseatedBy(Users users);

}

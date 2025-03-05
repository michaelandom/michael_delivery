package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.PasswordReset;
import com.michael_delivery.backend.domain.Users;
import com.michael_delivery.backend.model.PasswordResetDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PasswordResetRepository extends JpaRepository<PasswordReset, Long> {

    PasswordReset findFirstByUser(Users users);

    PasswordReset findFirstByReseatedBy(Users users);

    public Page<PasswordResetDTO> findAll(Specification<PasswordReset> spec, Pageable pageable);

}

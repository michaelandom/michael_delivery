package com.michael_delivery.backend.service;

import com.michael_delivery.backend.model.PasswordReset;
import com.michael_delivery.backend.model.Users;
import com.michael_delivery.backend.dto.PasswordResetDTO;
import com.michael_delivery.backend.repository.PasswordResetRepository;
import com.michael_delivery.backend.repository.UsersRepository;
import com.michael_delivery.backend.util.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
public class PasswordResetService extends BaseService<PasswordReset, PasswordResetDTO,Long, PasswordResetRepository> {

    private final PasswordResetRepository passwordResetRepository;
    private final UsersRepository usersRepository;

    public PasswordResetService(final PasswordResetRepository passwordResetRepository,
            final UsersRepository usersRepository) {
        super(passwordResetRepository,"passwordResetId");

        this.passwordResetRepository = passwordResetRepository;
        this.usersRepository = usersRepository;
    }

    @Override
    public Page<PasswordResetDTO> search(Specification<PasswordReset> query, Pageable pageable) {
        return this.passwordResetRepository.findAll(query, pageable);
    }

    @Override
    protected PasswordResetDTO mapToDTO(final PasswordReset passwordReset,
            final PasswordResetDTO passwordResetDTO) {
        passwordResetDTO.setPasswordResetId(passwordReset.getPasswordResetId());
        passwordResetDTO.setUser(passwordReset.getUser() == null ? null : passwordReset.getUser().getUserId());
        passwordResetDTO.setReseatedBy(passwordReset.getReseatedBy() == null ? null : passwordReset.getReseatedBy().getUserId());
        return passwordResetDTO;
    }

    @Override
    protected PasswordReset mapToEntity(final PasswordResetDTO passwordResetDTO,
            final PasswordReset passwordReset) {
//        passwordReset.setIsActive(passwordResetDTO.getIsActive());
//        passwordReset.setCode(passwordResetDTO.getCode());
        final Users user = passwordResetDTO.getUser() == null ? null : usersRepository.findById(passwordResetDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        passwordReset.setUser(user);
        final Users reseatedBy = passwordResetDTO.getReseatedBy() == null ? null : usersRepository.findById(passwordResetDTO.getReseatedBy())
                .orElseThrow(() -> new NotFoundException("reseatedBy not found"));
        passwordReset.setReseatedBy(reseatedBy);
        return passwordReset;
    }

    @Override
    protected PasswordResetDTO createDTO() {
        return new PasswordResetDTO();
    }

    @Override
    protected PasswordReset createEntity() {
        return new PasswordReset();
    }

}

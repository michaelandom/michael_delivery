package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.PasswordReset;
import com.michael_delivery.backend.domain.Users;
import com.michael_delivery.backend.model.PasswordResetDTO;
import com.michael_delivery.backend.repos.PasswordResetRepository;
import com.michael_delivery.backend.repos.UsersRepository;
import com.michael_delivery.backend.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PasswordResetService {

    private final PasswordResetRepository passwordResetRepository;
    private final UsersRepository usersRepository;

    public PasswordResetService(final PasswordResetRepository passwordResetRepository,
            final UsersRepository usersRepository) {
        this.passwordResetRepository = passwordResetRepository;
        this.usersRepository = usersRepository;
    }

    public List<PasswordResetDTO> findAll() {
        final List<PasswordReset> passwordResets = passwordResetRepository.findAll(Sort.by("passwordResetId"));
        return passwordResets.stream()
                .map(passwordReset -> mapToDTO(passwordReset, new PasswordResetDTO()))
                .toList();
    }

    public PasswordResetDTO get(final Long passwordResetId) {
        return passwordResetRepository.findById(passwordResetId)
                .map(passwordReset -> mapToDTO(passwordReset, new PasswordResetDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final PasswordResetDTO passwordResetDTO) {
        final PasswordReset passwordReset = new PasswordReset();
        mapToEntity(passwordResetDTO, passwordReset);
        return passwordResetRepository.save(passwordReset).getPasswordResetId();
    }

    public void update(final Long passwordResetId, final PasswordResetDTO passwordResetDTO) {
        final PasswordReset passwordReset = passwordResetRepository.findById(passwordResetId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(passwordResetDTO, passwordReset);
        passwordResetRepository.save(passwordReset);
    }

    public void delete(final Long passwordResetId) {
        passwordResetRepository.deleteById(passwordResetId);
    }

    private PasswordResetDTO mapToDTO(final PasswordReset passwordReset,
            final PasswordResetDTO passwordResetDTO) {
        passwordResetDTO.setPasswordResetId(passwordReset.getPasswordResetId());
        passwordResetDTO.setUser(passwordReset.getUser() == null ? null : passwordReset.getUser().getUserId());
        passwordResetDTO.setReseatedBy(passwordReset.getReseatedBy() == null ? null : passwordReset.getReseatedBy().getUserId());
        return passwordResetDTO;
    }

    private PasswordReset mapToEntity(final PasswordResetDTO passwordResetDTO,
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

}

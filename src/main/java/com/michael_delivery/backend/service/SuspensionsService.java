package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.Riders;
import com.michael_delivery.backend.domain.Suspensions;
import com.michael_delivery.backend.domain.Users;
import com.michael_delivery.backend.model.SuspensionsDTO;
import com.michael_delivery.backend.repos.RidersRepository;
import com.michael_delivery.backend.repos.SuspensionsRepository;
import com.michael_delivery.backend.repos.UsersRepository;
import com.michael_delivery.backend.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SuspensionsService {

    private final SuspensionsRepository suspensionsRepository;
    private final RidersRepository ridersRepository;
    private final UsersRepository usersRepository;

    public SuspensionsService(final SuspensionsRepository suspensionsRepository,
            final RidersRepository ridersRepository, final UsersRepository usersRepository) {
        this.suspensionsRepository = suspensionsRepository;
        this.ridersRepository = ridersRepository;
        this.usersRepository = usersRepository;
    }

    public List<SuspensionsDTO> findAll() {
        final List<Suspensions> suspensionses = suspensionsRepository.findAll(Sort.by("suspensionId"));
        return suspensionses.stream()
                .map(suspensions -> mapToDTO(suspensions, new SuspensionsDTO()))
                .toList();
    }

    public SuspensionsDTO get(final Long suspensionId) {
        return suspensionsRepository.findById(suspensionId)
                .map(suspensions -> mapToDTO(suspensions, new SuspensionsDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final SuspensionsDTO suspensionsDTO) {
        final Suspensions suspensions = new Suspensions();
        mapToEntity(suspensionsDTO, suspensions);
        return suspensionsRepository.save(suspensions).getSuspensionId();
    }

    public void update(final Long suspensionId, final SuspensionsDTO suspensionsDTO) {
        final Suspensions suspensions = suspensionsRepository.findById(suspensionId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(suspensionsDTO, suspensions);
        suspensionsRepository.save(suspensions);
    }

    public void delete(final Long suspensionId) {
        suspensionsRepository.deleteById(suspensionId);
    }

    private SuspensionsDTO mapToDTO(final Suspensions suspensions,
            final SuspensionsDTO suspensionsDTO) {
        suspensionsDTO.setSuspensionId(suspensions.getSuspensionId());
        suspensionsDTO.setReason(suspensions.getReason());
        suspensionsDTO.setIsSystemSuspenstion(suspensions.getIsSystemSuspenstion());
        suspensionsDTO.setReasonType(suspensions.getReasonType());
        suspensionsDTO.setStartingFrom(suspensions.getStartingFrom());
        suspensionsDTO.setEndingAt(suspensions.getEndingAt());
        suspensionsDTO.setIsActive(suspensions.getIsActive());
        suspensionsDTO.setRider(suspensions.getRider() == null ? null : suspensions.getRider().getRiderId());
        suspensionsDTO.setSuspenedBy(suspensions.getSuspenedBy() == null ? null : suspensions.getSuspenedBy().getUserId());
        return suspensionsDTO;
    }

    private Suspensions mapToEntity(final SuspensionsDTO suspensionsDTO,
            final Suspensions suspensions) {
        suspensions.setReason(suspensionsDTO.getReason());
        suspensions.setIsSystemSuspenstion(suspensionsDTO.getIsSystemSuspenstion());
        suspensions.setReasonType(suspensionsDTO.getReasonType());
        suspensions.setStartingFrom(suspensionsDTO.getStartingFrom());
        suspensions.setEndingAt(suspensionsDTO.getEndingAt());
        suspensions.setIsActive(suspensionsDTO.getIsActive());
        final Riders rider = suspensionsDTO.getRider() == null ? null : ridersRepository.findById(suspensionsDTO.getRider())
                .orElseThrow(() -> new NotFoundException("rider not found"));
        suspensions.setRider(rider);
        final Users suspenedBy = suspensionsDTO.getSuspenedBy() == null ? null : usersRepository.findById(suspensionsDTO.getSuspenedBy())
                .orElseThrow(() -> new NotFoundException("suspenedBy not found"));
        suspensions.setSuspenedBy(suspenedBy);
        return suspensions;
    }

}

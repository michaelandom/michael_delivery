package com.michael_delivery.backend.service;

import com.michael_delivery.backend.model.*;
import com.michael_delivery.backend.dto.SuspensionsDTO;
import com.michael_delivery.backend.repository.RidersRepository;
import com.michael_delivery.backend.repository.SuspensionsRepository;
import com.michael_delivery.backend.repository.UsersRepository;
import com.michael_delivery.backend.util.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
public class SuspensionsService extends BaseService<Suspensions, SuspensionsDTO,Long, SuspensionsRepository> {

    private final SuspensionsRepository suspensionsRepository;
    private final RidersRepository ridersRepository;
    private final UsersRepository usersRepository;

    public SuspensionsService(final SuspensionsRepository suspensionsRepository,
            final RidersRepository ridersRepository, final UsersRepository usersRepository) {
        super(suspensionsRepository,"suspensionId");
        this.suspensionsRepository = suspensionsRepository;
        this.ridersRepository = ridersRepository;
        this.usersRepository = usersRepository;
    }

    @Override
    public Page<SuspensionsDTO> search(Specification<Suspensions> query, Pageable pageable) {
        return this.suspensionsRepository.findAll(query, pageable);
    }

    @Override
    protected SuspensionsDTO mapToDTO(final Suspensions suspensions,
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

    @Override
    protected Suspensions mapToEntity(final SuspensionsDTO suspensionsDTO,
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

    @Override
    protected SuspensionsDTO createDTO() {
        return new SuspensionsDTO();
    }

    @Override
    protected Suspensions createEntity() {
        return new Suspensions();
    }

}

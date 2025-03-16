package com.michael_delivery.backend.service;

import com.michael_delivery.backend.model.*;
import com.michael_delivery.backend.dto.PenalitiesDTO;
import com.michael_delivery.backend.repository.PenalitiesRepository;
import com.michael_delivery.backend.repository.RidersRepository;
import com.michael_delivery.backend.repository.UsersRepository;
import com.michael_delivery.backend.util.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
public class PenalitiesService extends BaseService<Penalities, PenalitiesDTO,Long, PenalitiesRepository> {

    private final PenalitiesRepository penalitiesRepository;
    private final RidersRepository ridersRepository;
    private final UsersRepository usersRepository;

    public PenalitiesService(final PenalitiesRepository penalitiesRepository,
            final RidersRepository ridersRepository, final UsersRepository usersRepository) {
        super(penalitiesRepository,"penalitieId");
        this.penalitiesRepository = penalitiesRepository;
        this.ridersRepository = ridersRepository;
        this.usersRepository = usersRepository;
    }

    @Override
    public Page<PenalitiesDTO> search(Specification<Penalities> query, Pageable pageable) {
        return this.penalitiesRepository.findAll(query, pageable);
    }

    @Override
    protected PenalitiesDTO mapToDTO(final Penalities penalities, final PenalitiesDTO penalitiesDTO) {
        penalitiesDTO.setPenalitieId(penalities.getPenalitieId());
        penalitiesDTO.setReason(penalities.getReason());
        penalitiesDTO.setDeductedAmount(penalities.getDeductedAmount());
        penalitiesDTO.setDescription(penalities.getDescription());
        penalitiesDTO.setOrderNumber(penalities.getOrderNumber());
        penalitiesDTO.setIsWarning(penalities.getIsWarning());
        penalitiesDTO.setIsActive(penalities.getIsActive());
        penalitiesDTO.setRider(penalities.getRider() == null ? null : penalities.getRider().getRiderId());
        penalitiesDTO.setAdmin(penalities.getAdmin() == null ? null : penalities.getAdmin().getUserId());
        return penalitiesDTO;
    }

    @Override
    protected Penalities mapToEntity(final PenalitiesDTO penalitiesDTO, final Penalities penalities) {
        penalities.setReason(penalitiesDTO.getReason());
        penalities.setDeductedAmount(penalitiesDTO.getDeductedAmount());
        penalities.setDescription(penalitiesDTO.getDescription());
        penalities.setOrderNumber(penalitiesDTO.getOrderNumber());
        penalities.setIsWarning(penalitiesDTO.getIsWarning());
        penalities.setIsActive(penalitiesDTO.getIsActive());
        final Riders rider = penalitiesDTO.getRider() == null ? null : ridersRepository.findById(penalitiesDTO.getRider())
                .orElseThrow(() -> new NotFoundException("rider not found"));
        penalities.setRider(rider);
        final Users admin = penalitiesDTO.getAdmin() == null ? null : usersRepository.findById(penalitiesDTO.getAdmin())
                .orElseThrow(() -> new NotFoundException("admin not found"));
        penalities.setAdmin(admin);
        return penalities;
    }

    @Override
    protected PenalitiesDTO createDTO() {
        return new PenalitiesDTO();
    }

    @Override
    protected Penalities createEntity() {
        return new Penalities();
    }

}

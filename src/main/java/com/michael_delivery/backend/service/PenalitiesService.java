package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.Penalities;
import com.michael_delivery.backend.domain.Riders;
import com.michael_delivery.backend.domain.Users;
import com.michael_delivery.backend.model.PenalitiesDTO;
import com.michael_delivery.backend.repos.PenalitiesRepository;
import com.michael_delivery.backend.repos.RidersRepository;
import com.michael_delivery.backend.repos.UsersRepository;
import com.michael_delivery.backend.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PenalitiesService {

    private final PenalitiesRepository penalitiesRepository;
    private final RidersRepository ridersRepository;
    private final UsersRepository usersRepository;

    public PenalitiesService(final PenalitiesRepository penalitiesRepository,
            final RidersRepository ridersRepository, final UsersRepository usersRepository) {
        this.penalitiesRepository = penalitiesRepository;
        this.ridersRepository = ridersRepository;
        this.usersRepository = usersRepository;
    }

    public List<PenalitiesDTO> findAll() {
        final List<Penalities> penalitieses = penalitiesRepository.findAll(Sort.by("penalitieId"));
        return penalitieses.stream()
                .map(penalities -> mapToDTO(penalities, new PenalitiesDTO()))
                .toList();
    }

    public PenalitiesDTO get(final Long penalitieId) {
        return penalitiesRepository.findById(penalitieId)
                .map(penalities -> mapToDTO(penalities, new PenalitiesDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final PenalitiesDTO penalitiesDTO) {
        final Penalities penalities = new Penalities();
        mapToEntity(penalitiesDTO, penalities);
        return penalitiesRepository.save(penalities).getPenalitieId();
    }

    public void update(final Long penalitieId, final PenalitiesDTO penalitiesDTO) {
        final Penalities penalities = penalitiesRepository.findById(penalitieId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(penalitiesDTO, penalities);
        penalitiesRepository.save(penalities);
    }

    public void delete(final Long penalitieId) {
        penalitiesRepository.deleteById(penalitieId);
    }

    private PenalitiesDTO mapToDTO(final Penalities penalities, final PenalitiesDTO penalitiesDTO) {
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

    private Penalities mapToEntity(final PenalitiesDTO penalitiesDTO, final Penalities penalities) {
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

}

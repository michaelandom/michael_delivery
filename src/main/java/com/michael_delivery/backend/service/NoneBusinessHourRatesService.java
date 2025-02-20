package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.NoneBusinessHourRates;
import com.michael_delivery.backend.domain.Users;
import com.michael_delivery.backend.model.NoneBusinessHourRatesDTO;
import com.michael_delivery.backend.repos.NoneBusinessHourRatesRepository;
import com.michael_delivery.backend.repos.UsersRepository;
import com.michael_delivery.backend.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class NoneBusinessHourRatesService {

    private final NoneBusinessHourRatesRepository noneBusinessHourRatesRepository;
    private final UsersRepository usersRepository;

    public NoneBusinessHourRatesService(
            final NoneBusinessHourRatesRepository noneBusinessHourRatesRepository,
            final UsersRepository usersRepository) {
        this.noneBusinessHourRatesRepository = noneBusinessHourRatesRepository;
        this.usersRepository = usersRepository;
    }

    public List<NoneBusinessHourRatesDTO> findAll() {
        final List<NoneBusinessHourRates> noneBusinessHourRateses = noneBusinessHourRatesRepository.findAll(Sort.by("noneBusinessHourRateId"));
        return noneBusinessHourRateses.stream()
                .map(noneBusinessHourRates -> mapToDTO(noneBusinessHourRates, new NoneBusinessHourRatesDTO()))
                .toList();
    }

    public NoneBusinessHourRatesDTO get(final Long noneBusinessHourRateId) {
        return noneBusinessHourRatesRepository.findById(noneBusinessHourRateId)
                .map(noneBusinessHourRates -> mapToDTO(noneBusinessHourRates, new NoneBusinessHourRatesDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final NoneBusinessHourRatesDTO noneBusinessHourRatesDTO) {
        final NoneBusinessHourRates noneBusinessHourRates = new NoneBusinessHourRates();
        mapToEntity(noneBusinessHourRatesDTO, noneBusinessHourRates);
        return noneBusinessHourRatesRepository.save(noneBusinessHourRates).getNoneBusinessHourRateId();
    }

    public void update(final Long noneBusinessHourRateId,
            final NoneBusinessHourRatesDTO noneBusinessHourRatesDTO) {
        final NoneBusinessHourRates noneBusinessHourRates = noneBusinessHourRatesRepository.findById(noneBusinessHourRateId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(noneBusinessHourRatesDTO, noneBusinessHourRates);
        noneBusinessHourRatesRepository.save(noneBusinessHourRates);
    }

    public void delete(final Long noneBusinessHourRateId) {
        noneBusinessHourRatesRepository.deleteById(noneBusinessHourRateId);
    }

    private NoneBusinessHourRatesDTO mapToDTO(final NoneBusinessHourRates noneBusinessHourRates,
            final NoneBusinessHourRatesDTO noneBusinessHourRatesDTO) {
        noneBusinessHourRatesDTO.setNoneBusinessHourRateId(noneBusinessHourRates.getNoneBusinessHourRateId());
        noneBusinessHourRatesDTO.setStartTime(noneBusinessHourRates.getStartTime());
        noneBusinessHourRatesDTO.setEndTime(noneBusinessHourRates.getEndTime());
        noneBusinessHourRatesDTO.setRate(noneBusinessHourRates.getRate());
        noneBusinessHourRatesDTO.setIsLatest(noneBusinessHourRates.getIsLatest());
        noneBusinessHourRatesDTO.setCreatedBy(noneBusinessHourRates.getCreatedBy() == null ? null : noneBusinessHourRates.getCreatedBy().getUserId());
        return noneBusinessHourRatesDTO;
    }

    private NoneBusinessHourRates mapToEntity(
            final NoneBusinessHourRatesDTO noneBusinessHourRatesDTO,
            final NoneBusinessHourRates noneBusinessHourRates) {
        noneBusinessHourRates.setStartTime(noneBusinessHourRatesDTO.getStartTime());
        noneBusinessHourRates.setEndTime(noneBusinessHourRatesDTO.getEndTime());
        noneBusinessHourRates.setRate(noneBusinessHourRatesDTO.getRate());
        noneBusinessHourRates.setIsLatest(noneBusinessHourRatesDTO.getIsLatest());
        final Users createdBy = noneBusinessHourRatesDTO.getCreatedBy() == null ? null : usersRepository.findById(noneBusinessHourRatesDTO.getCreatedBy())
                .orElseThrow(() -> new NotFoundException("createdBy not found"));
        noneBusinessHourRates.setCreatedBy(createdBy);
        return noneBusinessHourRates;
    }

//    public boolean uniqueStartTimeEndTimeCheckExists(final String uniqueStartTimeEndTimeCheck) {
//
//              return noneBusinessHourRatesRepository.existsByUniqueStartTimeEndTimeCheckIgnoreCase(uniqueStartTimeEndTimeCheck);
//    }

}

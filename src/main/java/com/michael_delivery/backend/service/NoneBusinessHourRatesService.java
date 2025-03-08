package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.Destination;
import com.michael_delivery.backend.domain.NoneBusinessHourRates;
import com.michael_delivery.backend.domain.Users;
import com.michael_delivery.backend.model.DestinationDTO;
import com.michael_delivery.backend.model.NoneBusinessHourRatesDTO;
import com.michael_delivery.backend.repos.NoneBusinessHourRatesRepository;
import com.michael_delivery.backend.repos.UsersRepository;
import com.michael_delivery.backend.util.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;



@Service
public class NoneBusinessHourRatesService extends BaseService<NoneBusinessHourRates, NoneBusinessHourRatesDTO,Long, NoneBusinessHourRatesRepository> {

    private final NoneBusinessHourRatesRepository noneBusinessHourRatesRepository;
    private final UsersRepository usersRepository;

    public NoneBusinessHourRatesService(
            final NoneBusinessHourRatesRepository noneBusinessHourRatesRepository,
            final UsersRepository usersRepository) {
        super(noneBusinessHourRatesRepository,"noneBusinessHourRateId");
        this.noneBusinessHourRatesRepository = noneBusinessHourRatesRepository;
        this.usersRepository = usersRepository;
    }
    @Override
    public Page<NoneBusinessHourRatesDTO> search(Specification<NoneBusinessHourRates> query, Pageable pageable) {
        return this.noneBusinessHourRatesRepository.findAll(query, pageable);
    }

    @Override
    protected NoneBusinessHourRatesDTO mapToDTO(final NoneBusinessHourRates noneBusinessHourRates,
            final NoneBusinessHourRatesDTO noneBusinessHourRatesDTO) {
        noneBusinessHourRatesDTO.setNoneBusinessHourRateId(noneBusinessHourRates.getNoneBusinessHourRateId());
        noneBusinessHourRatesDTO.setStartTime(noneBusinessHourRates.getStartTime());
        noneBusinessHourRatesDTO.setEndTime(noneBusinessHourRates.getEndTime());
        noneBusinessHourRatesDTO.setRate(noneBusinessHourRates.getRate());
        noneBusinessHourRatesDTO.setIsLatest(noneBusinessHourRates.getIsLatest());
        noneBusinessHourRatesDTO.setCreatedBy(noneBusinessHourRates.getCreatedBy() == null ? null : noneBusinessHourRates.getCreatedBy().getUserId());
        return noneBusinessHourRatesDTO;
    }
    @Override
    protected NoneBusinessHourRates mapToEntity(
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

    @Override
    protected NoneBusinessHourRatesDTO createDTO() {
        return new NoneBusinessHourRatesDTO();
    }

    @Override
    protected NoneBusinessHourRates createEntity() {
        return new NoneBusinessHourRates();
    }


}

package com.michael_delivery.backend.service;

import com.michael_delivery.backend.model.RiderPayments;
import com.michael_delivery.backend.model.Riders;
import com.michael_delivery.backend.dto.RiderPaymentsDTO;
import com.michael_delivery.backend.repository.RiderPaymentsRepository;
import com.michael_delivery.backend.repository.RidersRepository;
import com.michael_delivery.backend.util.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
public class RiderPaymentsService extends BaseService<RiderPayments, RiderPaymentsDTO,Long, RiderPaymentsRepository> {

    private final RiderPaymentsRepository riderPaymentsRepository;
    private final RidersRepository ridersRepository;

    public RiderPaymentsService(final RiderPaymentsRepository riderPaymentsRepository,
            final RidersRepository ridersRepository) {
        super(riderPaymentsRepository,"riderPaymentId");
        this.riderPaymentsRepository = riderPaymentsRepository;
        this.ridersRepository = ridersRepository;
    }

    @Override
    public Page<RiderPaymentsDTO> search(Specification<RiderPayments> query, Pageable pageable) {
        return this.riderPaymentsRepository.findAll(query, pageable);
    }
    @Override
    protected RiderPaymentsDTO mapToDTO(final RiderPayments riderPayments,
            final RiderPaymentsDTO riderPaymentsDTO) {
        riderPaymentsDTO.setRiderPaymentId(riderPayments.getRiderPaymentId());
        riderPaymentsDTO.setDistance(riderPayments.getDistance());
        riderPaymentsDTO.setPrice(riderPayments.getPrice());
        riderPaymentsDTO.setIsExported(riderPayments.getIsExported());
        riderPaymentsDTO.setIsPaid(riderPayments.getIsPaid());
        riderPaymentsDTO.setPaymentCycle(riderPayments.getPaymentCycle());
        riderPaymentsDTO.setExportedAt(riderPayments.getExportedAt());
        riderPaymentsDTO.setRider(riderPayments.getRider() == null ? null : riderPayments.getRider().getRiderId());
        return riderPaymentsDTO;
    }

    @Override
    protected RiderPayments mapToEntity(final RiderPaymentsDTO riderPaymentsDTO,
            final RiderPayments riderPayments) {
        riderPayments.setDistance(riderPaymentsDTO.getDistance());
        riderPayments.setPrice(riderPaymentsDTO.getPrice());
        riderPayments.setIsExported(riderPaymentsDTO.getIsExported());
        riderPayments.setIsPaid(riderPaymentsDTO.getIsPaid());
        riderPayments.setPaymentCycle(riderPaymentsDTO.getPaymentCycle());
        riderPayments.setExportedAt(riderPaymentsDTO.getExportedAt());
        final Riders rider = riderPaymentsDTO.getRider() == null ? null : ridersRepository.findById(riderPaymentsDTO.getRider())
                .orElseThrow(() -> new NotFoundException("rider not found"));
        riderPayments.setRider(rider);
        return riderPayments;
    }

    @Override
    protected RiderPaymentsDTO createDTO() {
        return new RiderPaymentsDTO();
    }

    @Override
    protected RiderPayments createEntity() {
        return new RiderPayments();
    }

}

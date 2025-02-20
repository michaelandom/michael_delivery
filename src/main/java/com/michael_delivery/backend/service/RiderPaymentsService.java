package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.RiderPayments;
import com.michael_delivery.backend.domain.Riders;
import com.michael_delivery.backend.model.RiderPaymentsDTO;
import com.michael_delivery.backend.repos.RiderPaymentsRepository;
import com.michael_delivery.backend.repos.RidersRepository;
import com.michael_delivery.backend.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RiderPaymentsService {

    private final RiderPaymentsRepository riderPaymentsRepository;
    private final RidersRepository ridersRepository;

    public RiderPaymentsService(final RiderPaymentsRepository riderPaymentsRepository,
            final RidersRepository ridersRepository) {
        this.riderPaymentsRepository = riderPaymentsRepository;
        this.ridersRepository = ridersRepository;
    }

    public List<RiderPaymentsDTO> findAll() {
        final List<RiderPayments> riderPaymentses = riderPaymentsRepository.findAll(Sort.by("riderPaymentId"));
        return riderPaymentses.stream()
                .map(riderPayments -> mapToDTO(riderPayments, new RiderPaymentsDTO()))
                .toList();
    }

    public RiderPaymentsDTO get(final Long riderPaymentId) {
        return riderPaymentsRepository.findById(riderPaymentId)
                .map(riderPayments -> mapToDTO(riderPayments, new RiderPaymentsDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final RiderPaymentsDTO riderPaymentsDTO) {
        final RiderPayments riderPayments = new RiderPayments();
        mapToEntity(riderPaymentsDTO, riderPayments);
        return riderPaymentsRepository.save(riderPayments).getRiderPaymentId();
    }

    public void update(final Long riderPaymentId, final RiderPaymentsDTO riderPaymentsDTO) {
        final RiderPayments riderPayments = riderPaymentsRepository.findById(riderPaymentId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(riderPaymentsDTO, riderPayments);
        riderPaymentsRepository.save(riderPayments);
    }

    public void delete(final Long riderPaymentId) {
        riderPaymentsRepository.deleteById(riderPaymentId);
    }

    private RiderPaymentsDTO mapToDTO(final RiderPayments riderPayments,
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

    private RiderPayments mapToEntity(final RiderPaymentsDTO riderPaymentsDTO,
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

}

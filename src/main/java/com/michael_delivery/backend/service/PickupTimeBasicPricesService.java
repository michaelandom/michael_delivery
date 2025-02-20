package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.PickupTimeBasicPrices;
import com.michael_delivery.backend.model.PickupTimeBasicPricesDTO;
import com.michael_delivery.backend.repos.PickupTimeBasicPricesRepository;
import com.michael_delivery.backend.util.NotFoundException;
import com.michael_delivery.backend.util.ReferencedWarning;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PickupTimeBasicPricesService {

    private final PickupTimeBasicPricesRepository pickupTimeBasicPricesRepository;

    public PickupTimeBasicPricesService(
            final PickupTimeBasicPricesRepository pickupTimeBasicPricesRepository) {
        this.pickupTimeBasicPricesRepository = pickupTimeBasicPricesRepository;
    }

    public List<PickupTimeBasicPricesDTO> findAll() {
        final List<PickupTimeBasicPrices> pickupTimeBasicPriceses = pickupTimeBasicPricesRepository.findAll(Sort.by("pickupTimeBasicPriceId"));
        return pickupTimeBasicPriceses.stream()
                .map(pickupTimeBasicPrices -> mapToDTO(pickupTimeBasicPrices, new PickupTimeBasicPricesDTO()))
                .toList();
    }

    public PickupTimeBasicPricesDTO get(final Long pickupTimeBasicPriceId) {
        return pickupTimeBasicPricesRepository.findById(pickupTimeBasicPriceId)
                .map(pickupTimeBasicPrices -> mapToDTO(pickupTimeBasicPrices, new PickupTimeBasicPricesDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final PickupTimeBasicPricesDTO pickupTimeBasicPricesDTO) {
        final PickupTimeBasicPrices pickupTimeBasicPrices = new PickupTimeBasicPrices();
        mapToEntity(pickupTimeBasicPricesDTO, pickupTimeBasicPrices);
        return pickupTimeBasicPricesRepository.save(pickupTimeBasicPrices).getPickupTimeBasicPriceId();
    }

    public void update(final Long pickupTimeBasicPriceId,
            final PickupTimeBasicPricesDTO pickupTimeBasicPricesDTO) {
        final PickupTimeBasicPrices pickupTimeBasicPrices = pickupTimeBasicPricesRepository.findById(pickupTimeBasicPriceId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(pickupTimeBasicPricesDTO, pickupTimeBasicPrices);
        pickupTimeBasicPricesRepository.save(pickupTimeBasicPrices);
    }

    public void delete(final Long pickupTimeBasicPriceId) {
        pickupTimeBasicPricesRepository.deleteById(pickupTimeBasicPriceId);
    }

    private PickupTimeBasicPricesDTO mapToDTO(final PickupTimeBasicPrices pickupTimeBasicPrices,
            final PickupTimeBasicPricesDTO pickupTimeBasicPricesDTO) {
        pickupTimeBasicPricesDTO.setPickupTimeBasicPriceId(pickupTimeBasicPrices.getPickupTimeBasicPriceId());
        pickupTimeBasicPricesDTO.setPickupTime(pickupTimeBasicPrices.getPickupTime());
        pickupTimeBasicPricesDTO.setVehicleType(pickupTimeBasicPrices.getVehicleType());
        pickupTimeBasicPricesDTO.setPrice(pickupTimeBasicPrices.getPrice());
        pickupTimeBasicPricesDTO.setIsLatest(pickupTimeBasicPrices.getIsLatest());
        pickupTimeBasicPricesDTO.setPrevious(pickupTimeBasicPrices.getPrevious() == null ? null : pickupTimeBasicPrices.getPrevious().getPickupTimeBasicPriceId());
        return pickupTimeBasicPricesDTO;
    }

    private PickupTimeBasicPrices mapToEntity(
            final PickupTimeBasicPricesDTO pickupTimeBasicPricesDTO,
            final PickupTimeBasicPrices pickupTimeBasicPrices) {
        pickupTimeBasicPrices.setPickupTime(pickupTimeBasicPricesDTO.getPickupTime());
        pickupTimeBasicPrices.setVehicleType(pickupTimeBasicPricesDTO.getVehicleType());
        pickupTimeBasicPrices.setPrice(pickupTimeBasicPricesDTO.getPrice());
        pickupTimeBasicPrices.setIsLatest(pickupTimeBasicPricesDTO.getIsLatest());
        final PickupTimeBasicPrices previous = pickupTimeBasicPricesDTO.getPrevious() == null ? null : pickupTimeBasicPricesRepository.findById(pickupTimeBasicPricesDTO.getPrevious())
                .orElseThrow(() -> new NotFoundException("previous not found"));
        pickupTimeBasicPrices.setPrevious(previous);
        return pickupTimeBasicPrices;
    }


    public ReferencedWarning getReferencedWarning(final Long pickupTimeBasicPriceId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final PickupTimeBasicPrices pickupTimeBasicPrices = pickupTimeBasicPricesRepository.findById(pickupTimeBasicPriceId)
                .orElseThrow(NotFoundException::new);
        final PickupTimeBasicPrices previousPickupTimeBasicPrices = pickupTimeBasicPricesRepository.findFirstByPreviousAndPickupTimeBasicPriceIdNot(pickupTimeBasicPrices, pickupTimeBasicPrices.getPickupTimeBasicPriceId());
        if (previousPickupTimeBasicPrices != null) {
            referencedWarning.setKey("pickupTimeBasicPrices.pickupTimeBasicPrices.previous.referenced");
            referencedWarning.addParam(previousPickupTimeBasicPrices.getPickupTimeBasicPriceId());
            return referencedWarning;
        }
        return null;
    }

}

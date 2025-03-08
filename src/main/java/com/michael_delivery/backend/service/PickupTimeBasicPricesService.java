package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.CancellationRiderRequest;
import com.michael_delivery.backend.domain.Destination;
import com.michael_delivery.backend.domain.PickupTimeBasicPrices;
import com.michael_delivery.backend.model.CancellationRiderRequestDTO;
import com.michael_delivery.backend.model.DestinationDTO;
import com.michael_delivery.backend.model.PickupTimeBasicPricesDTO;
import com.michael_delivery.backend.repos.CancellationRiderRequestRepository;
import com.michael_delivery.backend.repos.PickupTimeBasicPricesRepository;
import com.michael_delivery.backend.util.NotFoundException;
import com.michael_delivery.backend.util.ReferencedWarning;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PickupTimeBasicPricesService extends BaseService<PickupTimeBasicPrices, PickupTimeBasicPricesDTO,Long, PickupTimeBasicPricesRepository> {

    private final PickupTimeBasicPricesRepository pickupTimeBasicPricesRepository;

    public PickupTimeBasicPricesService(
            final PickupTimeBasicPricesRepository pickupTimeBasicPricesRepository) {
        super(pickupTimeBasicPricesRepository,"pickupTimeBasicPriceId");
        this.pickupTimeBasicPricesRepository = pickupTimeBasicPricesRepository;
    }


    @Override
    public Page<PickupTimeBasicPricesDTO> search(Specification<PickupTimeBasicPrices> query, Pageable pageable) {
        return this.pickupTimeBasicPricesRepository.findAll(query, pageable);
    }

    @Override
    protected PickupTimeBasicPricesDTO mapToDTO(final PickupTimeBasicPrices pickupTimeBasicPrices,
            final PickupTimeBasicPricesDTO pickupTimeBasicPricesDTO) {
        pickupTimeBasicPricesDTO.setPickupTimeBasicPriceId(pickupTimeBasicPrices.getPickupTimeBasicPriceId());
        pickupTimeBasicPricesDTO.setPickupTime(pickupTimeBasicPrices.getPickupTime());
        pickupTimeBasicPricesDTO.setVehicleType(pickupTimeBasicPrices.getVehicleType());
        pickupTimeBasicPricesDTO.setPrice(pickupTimeBasicPrices.getPrice());
        pickupTimeBasicPricesDTO.setIsLatest(pickupTimeBasicPrices.getIsLatest());
        pickupTimeBasicPricesDTO.setPrevious(pickupTimeBasicPrices.getPrevious() == null ? null : pickupTimeBasicPrices.getPrevious().getPickupTimeBasicPriceId());
        return pickupTimeBasicPricesDTO;
    }

    @Override
    protected PickupTimeBasicPrices mapToEntity(
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

    @Override
    protected PickupTimeBasicPricesDTO createDTO() {
        return new PickupTimeBasicPricesDTO();
    }

    @Override
    protected PickupTimeBasicPrices createEntity() {
        return new PickupTimeBasicPrices();
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

package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.CancellationRiderRequest;
import com.michael_delivery.backend.domain.Destination;
import com.michael_delivery.backend.domain.TransportBasicPrices;
import com.michael_delivery.backend.model.CancellationRiderRequestDTO;
import com.michael_delivery.backend.model.DestinationDTO;
import com.michael_delivery.backend.model.TransportBasicPricesDTO;
import com.michael_delivery.backend.repos.CancellationRiderRequestRepository;
import com.michael_delivery.backend.repos.TransportBasicPricesRepository;
import com.michael_delivery.backend.util.NotFoundException;
import com.michael_delivery.backend.util.ReferencedWarning;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TransportBasicPricesService  extends BaseService<TransportBasicPrices, TransportBasicPricesDTO,Long, TransportBasicPricesRepository>{

    private final TransportBasicPricesRepository transportBasicPricesRepository;

    public TransportBasicPricesService(
            final TransportBasicPricesRepository transportBasicPricesRepository) {
        super(transportBasicPricesRepository,"transportBasicPriceId");
        this.transportBasicPricesRepository = transportBasicPricesRepository;
    }

    @Override
    public Page<TransportBasicPricesDTO> search(Specification<TransportBasicPrices> query, Pageable pageable) {
        return this.transportBasicPricesRepository.findAll(query, pageable);
    }

    @Override
    protected TransportBasicPricesDTO mapToDTO(final TransportBasicPrices transportBasicPrices,
            final TransportBasicPricesDTO transportBasicPricesDTO) {
        transportBasicPricesDTO.setTransportBasicPriceId(transportBasicPrices.getTransportBasicPriceId());
        transportBasicPricesDTO.setVehicleType(transportBasicPrices.getVehicleType());
        transportBasicPricesDTO.setBasicPrice(transportBasicPrices.getBasicPrice());
        transportBasicPricesDTO.setPreviousBasicPrice(transportBasicPrices.getPreviousBasicPrice());
        transportBasicPricesDTO.setPricePerMinute(transportBasicPrices.getPricePerMinute());
        transportBasicPricesDTO.setPickuptimeAsapPrice(transportBasicPrices.getPickuptimeAsapPrice());
        transportBasicPricesDTO.setPickuptime2hoursPrice(transportBasicPrices.getPickuptime2hoursPrice());
        transportBasicPricesDTO.setPickuptimeTodayPrice(transportBasicPrices.getPickuptimeTodayPrice());
        transportBasicPricesDTO.setPickuptimeOtherdayPrice(transportBasicPrices.getPickuptimeOtherdayPrice());
        transportBasicPricesDTO.setIsLatest(transportBasicPrices.getIsLatest());
        transportBasicPricesDTO.setPrevious(transportBasicPrices.getPrevious() == null ? null : transportBasicPrices.getPrevious().getTransportBasicPriceId());
        return transportBasicPricesDTO;
    }

    @Override
    protected TransportBasicPrices mapToEntity(final TransportBasicPricesDTO transportBasicPricesDTO,
            final TransportBasicPrices transportBasicPrices) {
        transportBasicPrices.setVehicleType(transportBasicPricesDTO.getVehicleType());
        transportBasicPrices.setBasicPrice(transportBasicPricesDTO.getBasicPrice());
        transportBasicPrices.setPreviousBasicPrice(transportBasicPricesDTO.getPreviousBasicPrice());
        transportBasicPrices.setPricePerMinute(transportBasicPricesDTO.getPricePerMinute());
        transportBasicPrices.setPickuptimeAsapPrice(transportBasicPricesDTO.getPickuptimeAsapPrice());
        transportBasicPrices.setPickuptime2hoursPrice(transportBasicPricesDTO.getPickuptime2hoursPrice());
        transportBasicPrices.setPickuptimeTodayPrice(transportBasicPricesDTO.getPickuptimeTodayPrice());
        transportBasicPrices.setPickuptimeOtherdayPrice(transportBasicPricesDTO.getPickuptimeOtherdayPrice());
        transportBasicPrices.setIsLatest(transportBasicPricesDTO.getIsLatest());
        final TransportBasicPrices previous = transportBasicPricesDTO.getPrevious() == null ? null : transportBasicPricesRepository.findById(transportBasicPricesDTO.getPrevious())
                .orElseThrow(() -> new NotFoundException("previous not found"));
        transportBasicPrices.setPrevious(previous);
        return transportBasicPrices;
    }

    @Override
    protected TransportBasicPricesDTO createDTO() {
        return new TransportBasicPricesDTO();
    }

    @Override
    protected TransportBasicPrices createEntity() {
        return new TransportBasicPrices();
    }


    public ReferencedWarning getReferencedWarning(final Long transportBasicPriceId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final TransportBasicPrices transportBasicPrices = transportBasicPricesRepository.findById(transportBasicPriceId)
                .orElseThrow(NotFoundException::new);
        final TransportBasicPrices previousTransportBasicPrices = transportBasicPricesRepository.findFirstByPreviousAndTransportBasicPriceIdNot(transportBasicPrices, transportBasicPrices.getTransportBasicPriceId());
        if (previousTransportBasicPrices != null) {
            referencedWarning.setKey("transportBasicPrices.transportBasicPrices.previous.referenced");
            referencedWarning.addParam(previousTransportBasicPrices.getTransportBasicPriceId());
            return referencedWarning;
        }
        return null;
    }

}

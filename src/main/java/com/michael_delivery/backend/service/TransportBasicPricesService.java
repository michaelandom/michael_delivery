package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.TransportBasicPrices;
import com.michael_delivery.backend.model.TransportBasicPricesDTO;
import com.michael_delivery.backend.repos.TransportBasicPricesRepository;
import com.michael_delivery.backend.util.NotFoundException;
import com.michael_delivery.backend.util.ReferencedWarning;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TransportBasicPricesService {

    private final TransportBasicPricesRepository transportBasicPricesRepository;

    public TransportBasicPricesService(
            final TransportBasicPricesRepository transportBasicPricesRepository) {
        this.transportBasicPricesRepository = transportBasicPricesRepository;
    }

    public List<TransportBasicPricesDTO> findAll() {
        final List<TransportBasicPrices> transportBasicPriceses = transportBasicPricesRepository.findAll(Sort.by("transportBasicPriceId"));
        return transportBasicPriceses.stream()
                .map(transportBasicPrices -> mapToDTO(transportBasicPrices, new TransportBasicPricesDTO()))
                .toList();
    }

    public TransportBasicPricesDTO get(final Long transportBasicPriceId) {
        return transportBasicPricesRepository.findById(transportBasicPriceId)
                .map(transportBasicPrices -> mapToDTO(transportBasicPrices, new TransportBasicPricesDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final TransportBasicPricesDTO transportBasicPricesDTO) {
        final TransportBasicPrices transportBasicPrices = new TransportBasicPrices();
        mapToEntity(transportBasicPricesDTO, transportBasicPrices);
        return transportBasicPricesRepository.save(transportBasicPrices).getTransportBasicPriceId();
    }

    public void update(final Long transportBasicPriceId,
            final TransportBasicPricesDTO transportBasicPricesDTO) {
        final TransportBasicPrices transportBasicPrices = transportBasicPricesRepository.findById(transportBasicPriceId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(transportBasicPricesDTO, transportBasicPrices);
        transportBasicPricesRepository.save(transportBasicPrices);
    }

    public void delete(final Long transportBasicPriceId) {
        transportBasicPricesRepository.deleteById(transportBasicPriceId);
    }

    private TransportBasicPricesDTO mapToDTO(final TransportBasicPrices transportBasicPrices,
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

    private TransportBasicPrices mapToEntity(final TransportBasicPricesDTO transportBasicPricesDTO,
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

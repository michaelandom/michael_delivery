package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.VehicleBasicPrices;
import com.michael_delivery.backend.model.VehicleBasicPricesDTO;
import com.michael_delivery.backend.repos.VehicleBasicPricesRepository;
import com.michael_delivery.backend.util.NotFoundException;
import com.michael_delivery.backend.util.ReferencedWarning;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class VehicleBasicPricesService {

    private final VehicleBasicPricesRepository vehicleBasicPricesRepository;

    public VehicleBasicPricesService(
            final VehicleBasicPricesRepository vehicleBasicPricesRepository) {
        this.vehicleBasicPricesRepository = vehicleBasicPricesRepository;
    }

    public List<VehicleBasicPricesDTO> findAll() {
        final List<VehicleBasicPrices> vehicleBasicPriceses = vehicleBasicPricesRepository.findAll(Sort.by("vehicleBasicPriceId"));
        return vehicleBasicPriceses.stream()
                .map(vehicleBasicPrices -> mapToDTO(vehicleBasicPrices, new VehicleBasicPricesDTO()))
                .toList();
    }

    public VehicleBasicPricesDTO get(final Long vehicleBasicPriceId) {
        return vehicleBasicPricesRepository.findById(vehicleBasicPriceId)
                .map(vehicleBasicPrices -> mapToDTO(vehicleBasicPrices, new VehicleBasicPricesDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final VehicleBasicPricesDTO vehicleBasicPricesDTO) {
        final VehicleBasicPrices vehicleBasicPrices = new VehicleBasicPrices();
        mapToEntity(vehicleBasicPricesDTO, vehicleBasicPrices);
        return vehicleBasicPricesRepository.save(vehicleBasicPrices).getVehicleBasicPriceId();
    }

    public void update(final Long vehicleBasicPriceId,
            final VehicleBasicPricesDTO vehicleBasicPricesDTO) {
        final VehicleBasicPrices vehicleBasicPrices = vehicleBasicPricesRepository.findById(vehicleBasicPriceId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(vehicleBasicPricesDTO, vehicleBasicPrices);
        vehicleBasicPricesRepository.save(vehicleBasicPrices);
    }

    public void delete(final Long vehicleBasicPriceId) {
        vehicleBasicPricesRepository.deleteById(vehicleBasicPriceId);
    }

    private VehicleBasicPricesDTO mapToDTO(final VehicleBasicPrices vehicleBasicPrices,
            final VehicleBasicPricesDTO vehicleBasicPricesDTO) {
        vehicleBasicPricesDTO.setVehicleBasicPriceId(vehicleBasicPrices.getVehicleBasicPriceId());
        vehicleBasicPricesDTO.setVehicleType(vehicleBasicPrices.getVehicleType());
        vehicleBasicPricesDTO.setPrice(vehicleBasicPrices.getPrice());
        vehicleBasicPricesDTO.setIsLatest(vehicleBasicPrices.getIsLatest());
        vehicleBasicPricesDTO.setPrevious(vehicleBasicPrices.getPrevious() == null ? null : vehicleBasicPrices.getPrevious().getVehicleBasicPriceId());
        return vehicleBasicPricesDTO;
    }

    private VehicleBasicPrices mapToEntity(final VehicleBasicPricesDTO vehicleBasicPricesDTO,
            final VehicleBasicPrices vehicleBasicPrices) {
        vehicleBasicPrices.setVehicleType(vehicleBasicPricesDTO.getVehicleType());
        vehicleBasicPrices.setPrice(vehicleBasicPricesDTO.getPrice());
        vehicleBasicPrices.setIsLatest(vehicleBasicPricesDTO.getIsLatest());
        final VehicleBasicPrices previous = vehicleBasicPricesDTO.getPrevious() == null ? null : vehicleBasicPricesRepository.findById(vehicleBasicPricesDTO.getPrevious())
                .orElseThrow(() -> new NotFoundException("previous not found"));
        vehicleBasicPrices.setPrevious(previous);
        return vehicleBasicPrices;
    }


    public ReferencedWarning getReferencedWarning(final Long vehicleBasicPriceId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final VehicleBasicPrices vehicleBasicPrices = vehicleBasicPricesRepository.findById(vehicleBasicPriceId)
                .orElseThrow(NotFoundException::new);
        final VehicleBasicPrices previousVehicleBasicPrices = vehicleBasicPricesRepository.findFirstByPreviousAndVehicleBasicPriceIdNot(vehicleBasicPrices, vehicleBasicPrices.getVehicleBasicPriceId());
        if (previousVehicleBasicPrices != null) {
            referencedWarning.setKey("vehicleBasicPrices.vehicleBasicPrices.previous.referenced");
            referencedWarning.addParam(previousVehicleBasicPrices.getVehicleBasicPriceId());
            return referencedWarning;
        }
        return null;
    }

}

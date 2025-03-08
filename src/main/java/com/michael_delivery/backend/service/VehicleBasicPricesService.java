package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.CancellationRiderRequest;
import com.michael_delivery.backend.domain.Destination;
import com.michael_delivery.backend.domain.VehicleBasicPrices;
import com.michael_delivery.backend.model.CancellationRiderRequestDTO;
import com.michael_delivery.backend.model.DestinationDTO;
import com.michael_delivery.backend.model.VehicleBasicPricesDTO;
import com.michael_delivery.backend.repos.CancellationRiderRequestRepository;
import com.michael_delivery.backend.repos.VehicleBasicPricesRepository;
import com.michael_delivery.backend.util.NotFoundException;
import com.michael_delivery.backend.util.ReferencedWarning;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class VehicleBasicPricesService extends BaseService<VehicleBasicPrices, VehicleBasicPricesDTO,Long, VehicleBasicPricesRepository> {

    private final VehicleBasicPricesRepository vehicleBasicPricesRepository;

    public VehicleBasicPricesService(
            final VehicleBasicPricesRepository vehicleBasicPricesRepository) {
        super(vehicleBasicPricesRepository,"vehicleBasicPriceId");
        this.vehicleBasicPricesRepository = vehicleBasicPricesRepository;
    }


    @Override
    public Page<VehicleBasicPricesDTO> search(Specification<VehicleBasicPrices> query, Pageable pageable) {
        return this.vehicleBasicPricesRepository.findAll(query, pageable);
    }

    @Override
    protected VehicleBasicPricesDTO mapToDTO(final VehicleBasicPrices vehicleBasicPrices,
            final VehicleBasicPricesDTO vehicleBasicPricesDTO) {
        vehicleBasicPricesDTO.setVehicleBasicPriceId(vehicleBasicPrices.getVehicleBasicPriceId());
        vehicleBasicPricesDTO.setVehicleType(vehicleBasicPrices.getVehicleType());
        vehicleBasicPricesDTO.setPrice(vehicleBasicPrices.getPrice());
        vehicleBasicPricesDTO.setIsLatest(vehicleBasicPrices.getIsLatest());
        vehicleBasicPricesDTO.setPrevious(vehicleBasicPrices.getPrevious() == null ? null : vehicleBasicPrices.getPrevious().getVehicleBasicPriceId());
        return vehicleBasicPricesDTO;
    }

    @Override
    protected VehicleBasicPrices mapToEntity(final VehicleBasicPricesDTO vehicleBasicPricesDTO,
            final VehicleBasicPrices vehicleBasicPrices) {
        vehicleBasicPrices.setVehicleType(vehicleBasicPricesDTO.getVehicleType());
        vehicleBasicPrices.setPrice(vehicleBasicPricesDTO.getPrice());
        vehicleBasicPrices.setIsLatest(vehicleBasicPricesDTO.getIsLatest());
        final VehicleBasicPrices previous = vehicleBasicPricesDTO.getPrevious() == null ? null : vehicleBasicPricesRepository.findById(vehicleBasicPricesDTO.getPrevious())
                .orElseThrow(() -> new NotFoundException("previous not found"));
        vehicleBasicPrices.setPrevious(previous);
        return vehicleBasicPrices;
    }

    @Override
    protected VehicleBasicPricesDTO createDTO() {
        return new VehicleBasicPricesDTO();
    }

    @Override
    protected VehicleBasicPrices createEntity() {
        return new VehicleBasicPrices();
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

package com.michael_delivery.backend.service;

import com.michael_delivery.backend.model.Riders;
import com.michael_delivery.backend.model.Vehicles;
import com.michael_delivery.backend.dto.VehiclesDTO;
import com.michael_delivery.backend.repository.RidersRepository;
import com.michael_delivery.backend.repository.VehiclesRepository;
import com.michael_delivery.backend.util.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
public class VehiclesService extends BaseService<Vehicles, VehiclesDTO,Long, VehiclesRepository>{

    private final VehiclesRepository vehiclesRepository;
    private final RidersRepository ridersRepository;

    public VehiclesService(final VehiclesRepository vehiclesRepository,
            final RidersRepository ridersRepository) {
        super(vehiclesRepository,"vehicleId");
        this.vehiclesRepository = vehiclesRepository;
        this.ridersRepository = ridersRepository;
    }

    @Override
    public Page<VehiclesDTO> search(Specification<Vehicles> query, Pageable pageable) {
        return this.vehiclesRepository.findAll(query, pageable);
    }



    @Override
    protected VehiclesDTO mapToDTO(final Vehicles vehicles, final VehiclesDTO vehiclesDTO) {
        vehiclesDTO.setVehicleId(vehicles.getVehicleId());
        vehiclesDTO.setIsCurrentVehicle(vehicles.getIsCurrentVehicle());
        vehiclesDTO.setVehicleType(vehicles.getVehicleType());
        vehiclesDTO.setModelYear(vehicles.getModelYear());
        vehiclesDTO.setManufacturer(vehicles.getManufacturer());
        vehiclesDTO.setTransportPhoto(vehicles.getTransportPhoto());
        vehiclesDTO.setDriverLicense(vehicles.getDriverLicense());
        vehiclesDTO.setInsurancePolicy(vehicles.getInsurancePolicy());
        vehiclesDTO.setDriverLicenseValidFrom(vehicles.getDriverLicenseValidFrom());
        vehiclesDTO.setDriverLicenseValidTo(vehicles.getDriverLicenseValidTo());
        vehiclesDTO.setInsurancePolicyValidFrom(vehicles.getInsurancePolicyValidFrom());
        vehiclesDTO.setInsurancePolicyValidTo(vehicles.getInsurancePolicyValidTo());
        vehiclesDTO.setExpiryDate(vehicles.getExpiryDate());
        vehiclesDTO.setRider(vehicles.getRider() == null ? null : vehicles.getRider().getRiderId());
        return vehiclesDTO;
    }

    @Override
    protected Vehicles mapToEntity(final VehiclesDTO vehiclesDTO, final Vehicles vehicles) {
        vehicles.setIsCurrentVehicle(vehiclesDTO.getIsCurrentVehicle());
        vehicles.setVehicleType(vehiclesDTO.getVehicleType());
        vehicles.setModelYear(vehiclesDTO.getModelYear());
        vehicles.setManufacturer(vehiclesDTO.getManufacturer());
        vehicles.setTransportPhoto(vehiclesDTO.getTransportPhoto());
        vehicles.setDriverLicense(vehiclesDTO.getDriverLicense());
        vehicles.setInsurancePolicy(vehiclesDTO.getInsurancePolicy());
        vehicles.setDriverLicenseValidFrom(vehiclesDTO.getDriverLicenseValidFrom());
        vehicles.setDriverLicenseValidTo(vehiclesDTO.getDriverLicenseValidTo());
        vehicles.setInsurancePolicyValidFrom(vehiclesDTO.getInsurancePolicyValidFrom());
        vehicles.setInsurancePolicyValidTo(vehiclesDTO.getInsurancePolicyValidTo());
        vehicles.setExpiryDate(vehiclesDTO.getExpiryDate());
        final Riders rider = vehiclesDTO.getRider() == null ? null : ridersRepository.findById(vehiclesDTO.getRider())
                .orElseThrow(() -> new NotFoundException("rider not found"));
        vehicles.setRider(rider);
        return vehicles;
    }

    @Override
    protected VehiclesDTO createDTO() {
        return new VehiclesDTO();
    }

    @Override
    protected Vehicles createEntity() {
        return new Vehicles();
    }

}

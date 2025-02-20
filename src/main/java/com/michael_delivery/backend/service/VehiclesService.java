package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.Riders;
import com.michael_delivery.backend.domain.Vehicles;
import com.michael_delivery.backend.model.VehiclesDTO;
import com.michael_delivery.backend.repos.RidersRepository;
import com.michael_delivery.backend.repos.VehiclesRepository;
import com.michael_delivery.backend.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class VehiclesService {

    private final VehiclesRepository vehiclesRepository;
    private final RidersRepository ridersRepository;

    public VehiclesService(final VehiclesRepository vehiclesRepository,
            final RidersRepository ridersRepository) {
        this.vehiclesRepository = vehiclesRepository;
        this.ridersRepository = ridersRepository;
    }

    public List<VehiclesDTO> findAll() {
        final List<Vehicles> vehicleses = vehiclesRepository.findAll(Sort.by("vehicleId"));
        return vehicleses.stream()
                .map(vehicles -> mapToDTO(vehicles, new VehiclesDTO()))
                .toList();
    }

    public VehiclesDTO get(final Long vehicleId) {
        return vehiclesRepository.findById(vehicleId)
                .map(vehicles -> mapToDTO(vehicles, new VehiclesDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final VehiclesDTO vehiclesDTO) {
        final Vehicles vehicles = new Vehicles();
        mapToEntity(vehiclesDTO, vehicles);
        return vehiclesRepository.save(vehicles).getVehicleId();
    }

    public void update(final Long vehicleId, final VehiclesDTO vehiclesDTO) {
        final Vehicles vehicles = vehiclesRepository.findById(vehicleId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(vehiclesDTO, vehicles);
        vehiclesRepository.save(vehicles);
    }

    public void delete(final Long vehicleId) {
        vehiclesRepository.deleteById(vehicleId);
    }

    private VehiclesDTO mapToDTO(final Vehicles vehicles, final VehiclesDTO vehiclesDTO) {
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

    private Vehicles mapToEntity(final VehiclesDTO vehiclesDTO, final Vehicles vehicles) {
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

}

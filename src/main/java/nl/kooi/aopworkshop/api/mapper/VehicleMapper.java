package nl.kooi.aopworkshop.api.mapper;

import nl.kooi.aopworkshop.api.dto.VehicleDTO;
import nl.kooi.aopworkshop.core.model.Vehicle;

public class VehicleMapper {

    public static VehicleDTO mapToDTO(Vehicle vehicle) {
        return new VehicleDTO(vehicle.licensePlate(), vehicle.brand(), vehicle.model());
    }

    public static Vehicle mapToModel(Long userId, VehicleDTO dto) {
        return new Vehicle(userId, dto.licensePlate(), dto.brand(), dto.model());
    }
}
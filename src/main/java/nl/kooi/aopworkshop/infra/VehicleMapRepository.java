package nl.kooi.aopworkshop.infra;

import nl.kooi.aopworkshop.core.model.Vehicle;
import nl.kooi.aopworkshop.core.port.VehicleRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class VehicleMapRepository implements VehicleRepository {

    private final Map<String, Vehicle> vehicleMap = new HashMap<>();

    @Override
    public Set<Vehicle> getVehiclesOwnedBy(Long ownerId) {
        return vehicleMap.values().stream()
                .filter(vehicle -> vehicle.ownerId().equals(ownerId))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getLicensePlatesForOwner(Long ownerId) {
        return vehicleMap.values().stream()
                .filter(vehicle -> vehicle.ownerId().equals(ownerId))
                .map(Vehicle::licensePlate)
                .collect(Collectors.toSet());
    }

    @Override
    public Optional<Vehicle> getVehicleWithLicensePlate(String licensePlate) {
        return Optional.ofNullable(licensePlate)
                .map(vehicleMap::get);
    }

    @Override
    public Vehicle saveVehicle(Vehicle vehicle) {
        vehicleMap.put(vehicle.licensePlate(), vehicle);
        return vehicle;
    }

    @Override
    public Vehicle updateVehicle(String licensePlate, Vehicle vehicle) {
        vehicleMap.remove(licensePlate);

        return saveVehicle(vehicle);
    }

    @Override
    public Boolean existsByLicensePlate(String licensePlate) {
        return vehicleMap.keySet().stream().anyMatch(licensePlate::equals);
    }
}

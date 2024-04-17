package nl.kooi.aopworkshop.core.port;

import nl.kooi.aopworkshop.core.model.Vehicle;

import java.util.Set;

public interface VehicleService {

    Set<Vehicle> getVehiclesOwnedBy(Long ownerId);

    Set<String> getLicensePlatesForOwner(Long ownerId);

    Vehicle getVehicleWithLicensePlate(String licensePlate);

    Vehicle saveVehicle(Vehicle vehicle);

    Vehicle updateVehicle(String licensePlate, Vehicle vehicle);
}

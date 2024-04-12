package nl.kooi.aopworkshop.core;

import lombok.RequiredArgsConstructor;
import nl.kooi.aopworkshop.core.exception.NotFoundException;
import nl.kooi.aopworkshop.core.exception.VehicleException;
import nl.kooi.aopworkshop.core.model.Vehicle;
import nl.kooi.aopworkshop.core.port.VehicleRepository;
import nl.kooi.aopworkshop.core.port.VehicleService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository repository;

    @Override
    public Set<Vehicle> getVehiclesOwnedBy(Long ownerId) {
        return repository.getVehiclesOwnedBy(ownerId);
    }

    @Override
    public Set<String> getLicensePlatesForOwner(Long ownerId) {
        return repository.getLicensePlatesForOwner(ownerId);
    }

    @Override
    public Vehicle getVehicleWithLicensePlate(String licensePlate) {
        return repository.getVehicleWithLicensePlate(licensePlate)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Vehicle saveVehicle(Vehicle vehicle) {
        if (repository.existsByLicensePlate(vehicle.licensePlate())) {
            throw new VehicleException("The license plate already exists");
        }

        return repository.saveVehicle(vehicle);
    }

    @Override
    public Vehicle updateVehicle(String licensePlate, Vehicle vehicle) {
        return Optional.ofNullable(licensePlate)
                .filter(repository::existsByLicensePlate)
                .map(lp -> repository.updateVehicle(lp, vehicle))
                .orElseThrow(NotFoundException::new);
    }
}

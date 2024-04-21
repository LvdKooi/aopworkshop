package nl.kooi.aopworkshop.core.model;

import java.util.Objects;

public record Vehicle(
        Long ownerId,
        String licensePlate,
        String brand,
        String model
) {

    public Vehicle {
        Objects.requireNonNull(licensePlate, "LicensePlate should not be null");
        Objects.requireNonNull(brand, "Brand should not be null");
        Objects.requireNonNull(model, "Model should not be null");
    }
}

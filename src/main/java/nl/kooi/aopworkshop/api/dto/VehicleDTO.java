package nl.kooi.aopworkshop.api.dto;

public record VehicleDTO(
        String licensePlate,
        String brand,
        String model) {

    public VehicleDTO withLicensePlate(String licensePlate) {
        return new VehicleDTO(licensePlate, brand, model);
    }
}

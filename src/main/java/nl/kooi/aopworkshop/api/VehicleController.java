package nl.kooi.aopworkshop.api;

import lombok.RequiredArgsConstructor;
import nl.kooi.aopworkshop.api.dto.VehicleDTO;
import nl.kooi.aopworkshop.api.mapper.VehicleMapper;
import nl.kooi.aopworkshop.core.port.VehicleService;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    @GetMapping
    public Set<VehicleDTO> getVehicleOfLoggedOnUser(@RequestHeader(name = "x-user-id") Long userId) {
        return vehicleService.getVehiclesOwnedBy(userId).stream().map(VehicleMapper::mapToDTO).collect(Collectors.toSet());
    }

    @GetMapping("/{licensePlate}")
    public VehicleDTO getVehicleByLicensePlate(@RequestHeader(name = "x-user-id") Long userId,
                                               @PathVariable("licensePlate") String licensePlate) {
        return VehicleMapper.mapToDTO(vehicleService.getVehicleWithLicensePlate(licensePlate));
    }

    @PutMapping("/{licensePlate}")
    public VehicleDTO updateVehicle(@RequestHeader(name = "x-user-id") Long userId,
                                    @PathVariable("licensePlate") String licensePlate,
                                    @RequestBody VehicleDTO vehicleDTO) {
        var updatedVehicle = vehicleService.updateVehicle(licensePlate, VehicleMapper.mapToModel(userId, vehicleDTO));

        return VehicleMapper.mapToDTO(updatedVehicle);
    }

    @PostMapping
    public VehicleDTO saveVehicle(@RequestHeader(name = "x-user-id") Long userId,
                                  @RequestBody VehicleDTO vehicleDTO) {
        var updatedVehicle = vehicleService.saveVehicle(VehicleMapper.mapToModel(userId, vehicleDTO));

        return VehicleMapper.mapToDTO(updatedVehicle);
    }
}
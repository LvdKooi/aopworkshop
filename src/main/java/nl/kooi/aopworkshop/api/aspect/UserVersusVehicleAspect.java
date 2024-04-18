package nl.kooi.aopworkshop.api.aspect;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.kooi.aopworkshop.api.exception.UnauthorizedException;
import nl.kooi.aopworkshop.core.port.VehicleService;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
@RequiredArgsConstructor
@Slf4j
public class UserVersusVehicleAspect {

    private final VehicleService vehicleService;

    @Before(value = "@annotation(nl.kooi.aopworkshop.api.aspect.UserIsAuthorized) && args(userId, licensePlate, ..)", argNames = "userId,licensePlate")
    public void checkIfUserIsOwnerOfVehicle(Long userId, String licensePlate) {
        var licensePlates = vehicleService.getLicensePlatesForOwner(userId);

        if(!licensePlates.contains(licensePlate)) {
            log.info("user {} tried to access vehicle {}", userId, licensePlate);
            throw new UnauthorizedException();
        }
    }

}

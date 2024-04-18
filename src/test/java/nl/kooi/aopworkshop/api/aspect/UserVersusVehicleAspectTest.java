package nl.kooi.aopworkshop.api.aspect;

import nl.kooi.aopworkshop.api.exception.UnauthorizedException;
import nl.kooi.aopworkshop.core.port.VehicleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringJUnitConfig(UserVersusVehicleAspect.class)
class UserVersusVehicleAspectTest {

    @Autowired
    private UserVersusVehicleAspect aspect;

    @MockBean
    private VehicleService vehicleService;

    @Test
    void whenUserDoesntBelongToLicensePlate_thenAnUnauthExceptionIsThrown(){
        when(vehicleService.getLicensePlatesForOwner(anyLong())).thenReturn(Set.of("AB"));

        assertThrows(UnauthorizedException.class, () -> aspect.checkIfUserIsOwnerOfVehicle(1L, "CD"));
    }
}
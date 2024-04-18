package nl.kooi.aopworkshop.api;

import nl.kooi.aopworkshop.api.aspect.UserVersusVehicleAspect;
import nl.kooi.aopworkshop.api.exception.UnauthorizedException;
import nl.kooi.aopworkshop.core.model.Vehicle;
import nl.kooi.aopworkshop.core.port.VehicleService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


@SpringJUnitConfig({VehicleController.class, UserVersusVehicleAspect.class})
class VehicleControllerTest {

    @Autowired
    private VehicleController controller;

    @Autowired
    private UserVersusVehicleAspect aspect;

    private VehicleController proxy;

    @MockBean
    private VehicleService service;

    @BeforeEach
    void setup() {
        var proxyFactory = new AspectJProxyFactory(controller);
        proxyFactory.addAspect(aspect);

        proxy = proxyFactory.getProxy();
    }
    @Test
    void shouldBeOwnerOfVehicle_getByLicensePlate() {
        when(service.getLicensePlatesForOwner(anyLong())).thenReturn(Set.of("CD"));

        assertThrows(UnauthorizedException.class, () -> proxy.getVehicleByLicensePlate(1L, "AB"));

        verify(service, never()).saveVehicle(any(Vehicle.class));
    }

}
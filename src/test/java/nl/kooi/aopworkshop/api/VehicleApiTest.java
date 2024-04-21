package nl.kooi.aopworkshop.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.kooi.aopworkshop.api.aspect.UserVersusVehicleAspect;
import nl.kooi.aopworkshop.api.dto.VehicleDTO;
import nl.kooi.aopworkshop.api.mapper.VehicleMapper;
import nl.kooi.aopworkshop.core.model.Vehicle;
import nl.kooi.aopworkshop.core.port.VehicleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@Import({VehicleMapper.class, UserVersusVehicleAspect.class, AopAutoConfiguration.class})
public class VehicleApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VehicleService vehicleService;

    @BeforeEach
    void setup() {
        when(vehicleService.getVehicleWithLicensePlate(anyString()))
                .thenReturn(new Vehicle(1L, "AB", "VW", "ID3"));
    }

    @Test
    void getByLicensePlate_shouldThrowUnauthorizedException_whenCallerIsNotOwnerOfVehicle() throws Exception {
        when(vehicleService.getLicensePlatesForOwner(anyLong())).thenReturn(Set.of("CD"));

        mockMvc.perform(get("/vehicles/AB").header("x-user-id", 1L))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getByLicensePlate_shouldRunSuccessfully_whenCallerIsOwnerOfVehicle() throws Exception {
        when(vehicleService.getLicensePlatesForOwner(anyLong())).thenReturn(Set.of("AB"));


        var json = mockMvc.perform(get("/vehicles/AB").header("x-user-id", 1L))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var dto = objectMapper.readValue(json, VehicleDTO.class);

        assertThat(dto)
                .extracting(
                        VehicleDTO::licensePlate,
                        VehicleDTO::brand,
                        VehicleDTO::model
                )
                .containsExactly(
                        "AB",
                        "VW",
                        "ID3"
                );
    }
}

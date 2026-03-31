package mumm.weatherstation.integration;

import jakarta.transaction.Transactional;
import mumm.weatherstation.controller.dto.StationDto;
import mumm.weatherstation.controller.dto.StationRequest;
import mumm.weatherstation.service.StationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class StationServiceIntegrationTest {

    @Autowired
    private StationService stationService;

    @Test
    void shouldInsertAndGetStation() {
        StationRequest request = new StationRequest("Test Tallinn", 59.437, 24.7536);

        StationDto inserted = stationService.insert(request);
        StationDto fetched = stationService.get(inserted.id());

        assertNotNull(inserted.id());
        assertEquals("Test Tallinn", inserted.name());
        assertEquals(59.437, inserted.latitude());
        assertEquals(24.7536, inserted.longitude());

        assertEquals(inserted.id(), fetched.id());
        assertEquals(inserted.name(), fetched.name());
        assertEquals(inserted.latitude(), fetched.latitude());
        assertEquals(inserted.longitude(), fetched.longitude());
    }

    @Test
    void shouldFetchAll() {
        assertEquals(0, stationService.getAll().size());

        StationDto inserted = stationService.insert(
                new StationRequest("Fetch test station", 58.3776, 26.7290)
        );

        List<StationDto> stations = stationService.getAll();

        assertEquals(1, stations.size());

        StationDto fetched = stations.get(0);

        assertEquals(inserted.id(), fetched.id());
        assertEquals("Fetch test station", fetched.name());
        assertEquals(58.3776, fetched.latitude());
        assertEquals(26.7290, fetched.longitude());
    }

    @Test
    void shouldDeleteStation() {
        StationDto inserted = stationService.insert(
                new StationRequest("Delete test station", 59.437, 24.7536)
        );

        assertEquals(1, stationService.getAll().size());

        stationService.delete(inserted.id());

        assertEquals(0, stationService.getAll().size());
    }
}
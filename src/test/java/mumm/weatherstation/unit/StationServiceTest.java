package mumm.weatherstation.unit;

import mumm.weatherstation.controller.dto.StationDto;
import mumm.weatherstation.controller.dto.StationRequest;
import mumm.weatherstation.entity.Station;
import mumm.weatherstation.repository.StationRepository;
import mumm.weatherstation.service.StationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StationServiceTest {

    @Mock
    private StationRepository stationRepository;

    @InjectMocks
    private StationService stationService;

    @Test
    void shouldGetStationById() {
        // Given
        Long id = 1L;
        Station station = mock(Station.class);

        when(station.getId()).thenReturn(1L);
        when(station.getName()).thenReturn("Tallinn");
        when(station.getLatitude()).thenReturn(59.437);
        when(station.getLongitude()).thenReturn(24.7536);

        when(stationRepository.findById(id)).thenReturn(Optional.of(station));

        // When
        StationDto result = stationService.get(id);

        // Then
        assertEquals(1L, result.id());
        assertEquals("Tallinn", result.name());
        assertEquals(59.437, result.latitude());
        assertEquals(24.7536, result.longitude());
        verify(stationRepository).findById(id);
    }

    @Test
    void shouldThrowWhenStationNotFound() {
        // Given
        Long id = 999L;
        when(stationRepository.findById(id)).thenReturn(Optional.empty());

        // When
        NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> stationService.get(id)
        );

        // Then
        assertEquals("Station not found: " + id, exception.getMessage());
        verify(stationRepository).findById(id);
    }

    @Test
    void shouldThrowWhenSomeStationsAreMissing() {
        // Given
        Set<Long> ids = Set.of(1L, 2L);

        Station foundStation = mock(Station.class);
        when(foundStation.getId()).thenReturn(1L);

        when(stationRepository.findAllById(ids)).thenReturn(List.of(foundStation));

        // When
        NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> stationService.get(ids)
        );

        // Then
        assertEquals("Stations not found: [2]", exception.getMessage());
        verify(stationRepository).findAllById(ids);
    }

    @Test
    void shouldGetAllStations() {
        // Given
        Station station1 = mock(Station.class);
        Station station2 = mock(Station.class);

        when(station1.getId()).thenReturn(1L);
        when(station1.getName()).thenReturn("Tallinn");

        when(station2.getId()).thenReturn(2L);
        when(station2.getName()).thenReturn("Tartu");

        when(stationRepository.findAll()).thenReturn(List.of(station1, station2));

        // When
        List<StationDto> result = stationService.getAll();

        // Then
        assertEquals(2, result.size());
        assertEquals("Tallinn", result.get(0).name());
        assertEquals("Tartu", result.get(1).name());
        verify(stationRepository).findAll();
    }

    @Test
    void shouldThrowWhenUpdatingMissingStation() {
        // Given
        Long id = 999L;
        StationRequest request = new StationRequest("Updated Tallinn", 60.0, 25.0);

        when(stationRepository.findById(id)).thenReturn(Optional.empty());

        // When
        NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> stationService.update(id, request)
        );

        // Then
        assertEquals("Station not found: " + id, exception.getMessage());
        verify(stationRepository).findById(id);
    }

    @Test
    void shouldDeleteStationById() {
        // Given
        Long id = 1L;

        // When
        stationService.delete(id);

        // Then
        verify(stationRepository).deleteById(id);
    }
}
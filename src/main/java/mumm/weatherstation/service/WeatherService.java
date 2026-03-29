package mumm.weatherstation.service;

import mumm.weatherstation.controller.dto.StationDto;
import mumm.weatherstation.controller.dto.WeatherDto;
import mumm.weatherstation.service.adapter.WeatherAdapter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class WeatherService {

    private final StationService stationService;

    private final WeatherAdapter weatherAdapter;

    public WeatherService(StationService stationService, WeatherAdapter weatherAdapter) {
        this.stationService = stationService;
        this.weatherAdapter = weatherAdapter;
    }

    public List<WeatherDto> getWeatherData(Long stationId) {
        List<StationDto> stations = stationService.get(Set.of(stationId));

        return this.weatherAdapter.getWeather(stations);
    }

}
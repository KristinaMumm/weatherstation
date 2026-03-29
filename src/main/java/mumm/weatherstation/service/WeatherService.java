package mumm.weatherstation.service;

import mumm.weatherstation.client.openmeteo.OpenMeteoClient;
import mumm.weatherstation.controller.dto.StationDto;
import mumm.weatherstation.controller.dto.WeatherBatchRequest;
import mumm.weatherstation.controller.dto.WeatherDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeatherService {

	private final StationService stationService;

	private final OpenMeteoClient weatherDataClient;

	public WeatherService(StationService stationService, OpenMeteoClient weatherDataClient) {
		this.stationService = stationService;
		this.weatherDataClient = weatherDataClient;
	}

	public List<WeatherDto> getWeatherData(WeatherBatchRequest request) {
		List<StationDto> stations = stationService.get(request.stationIds());

		return this.weatherDataClient.getWeatherData(stations);
	}

}
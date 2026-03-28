package mumm.weatherstation.service;

import mumm.weatherstation.client.openmeteo.OpenMeteoClient;
import mumm.weatherstation.client.openmeteo.WeatherResponse;
import mumm.weatherstation.controller.dto.WeatherDto;
import mumm.weatherstation.mapper.WeatherMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeatherService {

	private final OpenMeteoClient weatherDataClient;
	private final WeatherMapper weatherMapper;

	public WeatherService(OpenMeteoClient weatherDataClient, WeatherMapper weatherMapper) {
		this.weatherDataClient = weatherDataClient;
		this.weatherMapper = weatherMapper;
	}

	public List<WeatherDto> getWeatherData() {
		List<WeatherResponse> response = this.weatherDataClient.getWeatherData();

		return response.stream()
				.map(r -> this.weatherMapper.toResponse(123L, r))
				.toList();
	}
}
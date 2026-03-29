package mumm.weatherstation.service;

import mumm.weatherstation.controller.dto.StationDto;
import mumm.weatherstation.controller.dto.WeatherBatchRequest;
import mumm.weatherstation.controller.dto.WeatherDto;
import mumm.weatherstation.service.adapter.WeatherAdapter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeatherService {

	private final StationService stationService;

	private final WeatherAdapter weatherAdapter;

	public WeatherService(StationService stationService, WeatherAdapter weatherAdapter) {
		this.stationService = stationService;
		this.weatherAdapter = weatherAdapter;
	}

	public List<WeatherDto> getWeatherData(WeatherBatchRequest request) {
		List<StationDto> stations = stationService.get(request.stationIds());

		return this.weatherAdapter.getWeather(stations);
	}

}
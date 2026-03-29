package mumm.weatherstation.service.adapter;

import mumm.weatherstation.controller.dto.StationDto;
import mumm.weatherstation.controller.dto.WeatherDto;

import java.util.List;

public interface WeatherAdapter {

	List<WeatherDto> getWeather(List<StationDto> stations);

}

package mumm.weatherstation.mapper;

import mumm.weatherstation.client.openmeteo.WeatherResponse;
import mumm.weatherstation.controller.dto.WeatherDto;
import org.springframework.stereotype.Component;

@Component
public class WeatherMapper {

	public WeatherDto toResponse(Long stationId, WeatherResponse response) {
		return new WeatherDto(stationId, response.getCurrent().getTemperature(), response.getCurrent().getWindSpeed(),
				response.getCurrent().getPrecipitation());
	}

}

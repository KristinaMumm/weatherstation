package mumm.weatherstation.client.openmeteo;

import mumm.weatherstation.controller.dto.StationDto;
import mumm.weatherstation.controller.dto.WeatherDto;
import org.springframework.stereotype.Component;

@Component
public class WeatherMapper {

    public WeatherDto toDto(StationDto station, WeatherResponse response) {
        return new WeatherDto(
                station.id(),
                response.current().temperature(),
                response.current().windSpeed(),
                response.current().precipitation()
        );
    }

}
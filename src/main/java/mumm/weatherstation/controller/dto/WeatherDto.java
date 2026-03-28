package mumm.weatherstation.controller.dto;

public record WeatherDto(Long stationId, Double temperature, Double windSpeed, Double precipitation) {
}

package mumm.weatherstation.controller.dto;

import java.util.Set;

public record WeatherBatchRequest(Set<Long> stationIds) {}

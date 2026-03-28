package mumm.weatherstation.controller.dto;

public record StationResponse(
        Long id,
        String name,
        Double latitude,
        Double longitude
) {}
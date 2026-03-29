package mumm.weatherstation.controller.dto;

public record StationDto(
        Long id,
        String name,
        Double latitude,
        Double longitude
) {
}

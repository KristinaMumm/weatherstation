package mumm.weatherstation.controller.dto;

public record StationRequest(String name, Double latitude, Double longitude) {
}
package mumm.weatherstation.mapper;

import mumm.weatherstation.controller.dto.StationDto;
import mumm.weatherstation.controller.dto.StationRequest;
import mumm.weatherstation.entity.Station;


public final class StationMapper {

    private StationMapper() {
    }

    public static Station toEntity(StationRequest request) {
        return new Station(
                request.name(),
                request.latitude(),
                request.longitude()
        );
    }

    public static StationDto toResponse(Station station) {
        return new StationDto(
                station.getId(),
                station.getName(),
                station.getLatitude(),
                station.getLongitude()
        );
    }

}

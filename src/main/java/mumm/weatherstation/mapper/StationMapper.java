package mumm.weatherstation.mapper;

import mumm.weatherstation.controller.dto.StationDto;
import mumm.weatherstation.controller.dto.StationRequest;
import mumm.weatherstation.entity.Station;
import org.springframework.stereotype.Component;

@Component
public class StationMapper {

    public Station toEntity(StationRequest request) {
        return new Station(
                request.name(),
                request.latitude(),
                request.longitude()
        );
    }

    public StationDto toResponse(Station station) {
        return new StationDto(
                station.getId(),
                station.getName(),
                station.getLatitude(),
                station.getLongitude()
        );
    }

}

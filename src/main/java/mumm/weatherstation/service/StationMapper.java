package mumm.weatherstation.service;

import mumm.weatherstation.controller.dto.StationRequest;
import mumm.weatherstation.controller.dto.StationResponse;
import mumm.weatherstation.entity.Station;
import org.springframework.stereotype.Component;

@Component
public class StationMapper {

	public Station toEntity(StationRequest request) {
		return new Station(request.name(), request.latitude(), request.longitude());
	}

	public StationResponse toResponse(Station station) {
		return new StationResponse(station.getId(), station.getName(), station.getLatitude(), station.getLongitude());
	}

}

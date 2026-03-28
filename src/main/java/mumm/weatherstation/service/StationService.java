package mumm.weatherstation.service;

import mumm.weatherstation.controller.dto.StationRequest;
import mumm.weatherstation.controller.dto.StationResponse;
import mumm.weatherstation.entity.Station;
import mumm.weatherstation.repository.StationRepository;
import mumm.weatherstation.mapper.StationMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class StationService {

	private final StationRepository stationRepository;

	private final StationMapper stationMapper;

	public StationService(StationRepository stationRepository, StationMapper stationMapper) {
		this.stationRepository = stationRepository;
		this.stationMapper = stationMapper;
	}

	public StationResponse get(Long id) {
		return stationMapper.toResponse(getEntity(id));
	}

	public List<StationResponse> getAll() {
		return stationRepository.findAll().stream().map(stationMapper::toResponse).toList();
	}

	public StationResponse insert(StationRequest request) {
		Station station = stationMapper.toEntity(request);
		return stationMapper.toResponse(stationRepository.save(station));
	}

	public StationResponse update(Long id, StationRequest request) {
		Station existing = getEntity(id);

		existing.update(request.name(), request.latitude(), request.longitude());

		return stationMapper.toResponse(stationRepository.save(existing));
	}

	private Station getEntity(Long id) {
		return stationRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Station not found: " + id));
	}

}

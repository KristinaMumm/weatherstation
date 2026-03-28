package mumm.weatherstation.service;

import mumm.weatherstation.controller.dto.StationRequest;
import mumm.weatherstation.controller.dto.StationResponse;
import mumm.weatherstation.entity.Station;
import mumm.weatherstation.repository.StationRepository;
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
		Station station = stationRepository.findById(id).orElseThrow(NoSuchElementException::new);

		return stationMapper.toResponse(station);
	}

	public List<StationResponse> getAll() {
		return stationRepository.findAll().stream().map(stationMapper::toResponse).toList();
	}

	public StationResponse insert(StationRequest request) {
		Station station = stationMapper.toEntity(request);

		Station saved = stationRepository.save(station);

		return stationMapper.toResponse(saved);
	}

	public void update(Long id) {
	}

}

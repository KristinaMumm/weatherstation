package mumm.weatherstation.service;

import mumm.weatherstation.controller.dto.StationDto;
import mumm.weatherstation.controller.dto.StationRequest;
import mumm.weatherstation.entity.Station;
import mumm.weatherstation.mapper.StationMapper;
import mumm.weatherstation.repository.StationRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StationService {

    private final StationRepository stationRepository;

    private final StationMapper stationMapper;

    public StationService(StationRepository stationRepository, StationMapper stationMapper) {
        this.stationRepository = stationRepository;
        this.stationMapper = stationMapper;
    }

    public StationDto get(Long id) {
        return stationMapper.toResponse(getEntity(id));
    }

    public List<StationDto> get(Set<Long> ids) {

        List<Station> stations = stationRepository.findAllById(ids);

        if (stations.size() != ids.size()) {
            Set<Long> foundIds = stations.stream()
                    .map(Station::getId)
                    .collect(Collectors.toSet());

            Set<Long> missing = new HashSet<>(ids);
            missing.removeAll(foundIds);

            throw new NoSuchElementException(
                    "Stations not found: " + missing
            );
        }

        return stations.stream()
                .map(stationMapper::toResponse)
                .toList();
    }

    public List<StationDto> getAll() {
        return stationRepository.findAll().stream().map(stationMapper::toResponse).toList();
    }

    public StationDto insert(StationRequest request) {
        Station station = stationMapper.toEntity(request);
        return stationMapper.toResponse(stationRepository.save(station));
    }

    public StationDto update(Long id, StationRequest request) {
        Station existing = getEntity(id);

        existing.update(request.name(), request.latitude(), request.longitude());

        return stationMapper.toResponse(stationRepository.save(existing));
    }

    private Station getEntity(Long id) {
        return stationRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Station not found: " + id));
    }

}

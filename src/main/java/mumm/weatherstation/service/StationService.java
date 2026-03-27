package mumm.weatherstation.service;

import mumm.weatherstation.entity.Station;
import mumm.weatherstation.repository.StationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationService {
    private final StationRepository stationRepository;

    public StationService(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    public void get(Long id) {}

    public List<Station> getAll() {
        return this.stationRepository.findAll();
    }

    public void insert() {}

    public void update(Long id) {}
}

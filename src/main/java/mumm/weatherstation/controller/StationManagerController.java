package mumm.weatherstation.controller;

import mumm.weatherstation.controller.dto.Response;
import mumm.weatherstation.entity.Station;
import mumm.weatherstation.service.StationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StationManagerController{
    private final StationService stationService;

    public StationManagerController(StationService stationService) {
        this.stationService = stationService;
    }

    @GetMapping("stations/{id}")
    public Response<String> getStation(@PathVariable Long id) {
        this.stationService.get(id);
        return new Response<>("get 1 station");
    }

    @GetMapping("stations/list")
    public Response<List<Station>> getStations() {
        return new Response<>(this.stationService.getAll());
    }

    @PutMapping("stations/new")
    public Response<String> insertStation() {
        this.stationService.insert();
        return new Response<>("insert station");
    }

    @PostMapping("stations/{id}")
    public Response<String> updateStation(@PathVariable Long id) {
        this.stationService.update(id);
        return new Response<>("update station");
    }
}

package mumm.weatherstation.controller;

import mumm.weatherstation.controller.dto.Response;
import mumm.weatherstation.controller.dto.StationDto;
import mumm.weatherstation.controller.dto.StationRequest;
import mumm.weatherstation.service.StationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StationManagerController {

    private final StationService stationService;

    public StationManagerController(StationService stationService) {
        this.stationService = stationService;
    }

    @GetMapping("stations/{id}")
    public Response<StationDto> getStation(
            @PathVariable Long id
    ) {
        return Response.success(this.stationService.get(id));
    }

    @GetMapping("stations/list")
    public Response<List<StationDto>> getStations() {
        return Response.success(this.stationService.getAll());
    }

    @PostMapping("stations")
    public Response<StationDto> insertStation(
            @RequestBody StationRequest request
    ) {
        StationDto newStation = this.stationService.insert(request);
        return Response.success(newStation);
    }

    @PutMapping("stations/{id}")
    public Response<StationDto> updateStation(
            @PathVariable Long id,
            @RequestBody StationRequest request
    ) {
        return Response.success(this.stationService.update(id, request));
    }

    @DeleteMapping("stations/{id}")
    public Response<Void> deleteStation(
            @PathVariable Long id
    ) {
        this.stationService.delete(id);
        return Response.success(null);
    }

}

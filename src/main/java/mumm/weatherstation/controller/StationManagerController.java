package mumm.weatherstation.controller;

import mumm.weatherstation.controller.dto.Response;
import mumm.weatherstation.entity.Station;
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
	public Response<Station> getStation(@PathVariable Long id) {
		return Response.success(this.stationService.get(id));
	}

	@GetMapping("stations/list")
	public Response<List<Station>> getStations() {
		return Response.success(this.stationService.getAll());
	}

	@PutMapping("stations/new")
	public Response<String> insertStation() {
		this.stationService.insert();
		return Response.success("insert station");
	}

	@PostMapping("stations/{id}")
	public Response<String> updateStation(@PathVariable Long id) {
		this.stationService.update(id);
		return Response.success("update station");
	}

}

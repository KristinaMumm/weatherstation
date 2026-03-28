package mumm.weatherstation.controller;

import mumm.weatherstation.controller.dto.Response;
import mumm.weatherstation.controller.dto.StationRequest;
import mumm.weatherstation.controller.dto.StationResponse;
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
	public Response<StationResponse> getStation(@PathVariable Long id) {
		return Response.success(this.stationService.get(id));
	}

	@GetMapping("stations/list")
	public Response<List<StationResponse>> getStations() {
		return Response.success(this.stationService.getAll());
	}

	@PutMapping("stations/new")
	public Response<StationResponse> insertStation(@RequestBody StationRequest request) {
		StationResponse newStation = this.stationService.insert(request);
		return Response.success(newStation);
	}

	@PostMapping("stations/{id}")
	public Response<StationResponse> updateStation(@PathVariable Long id, @RequestBody StationRequest request) {
		return Response.success(this.stationService.update(id, request));
	}

}

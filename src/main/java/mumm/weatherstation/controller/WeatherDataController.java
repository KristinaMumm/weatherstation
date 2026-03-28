package mumm.weatherstation.controller;

import mumm.weatherstation.controller.dto.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherDataController {

	@GetMapping("/stations/{id}/weather")
	public Response<String> getStationWeather(@PathVariable String id) {
		return Response.success("Hello World!");
	}

	@PostMapping("/weather/batch")
	public Response<String> getStationWeatherBatch() {
		return Response.success("Hello World!");
	}

}

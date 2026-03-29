package mumm.weatherstation.controller;

import mumm.weatherstation.controller.dto.Response;
import mumm.weatherstation.controller.dto.WeatherBatchRequest;
import mumm.weatherstation.controller.dto.WeatherDto;
import mumm.weatherstation.service.WeatherService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WeatherDataController {

    private final WeatherService weatherService;

    public WeatherDataController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/stations/{id}/weather")
    public Response<String> getStationWeather(
            @PathVariable String id
    ) {
        return Response.success("Hello World!");
    }

    @PostMapping("/weather/batch")
    public Response<List<WeatherDto>> getStationWeatherBatch(
            @RequestBody WeatherBatchRequest request
    ) {
        return Response.success(this.weatherService.getWeatherData(request));
    }

}

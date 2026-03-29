package mumm.weatherstation.controller;

import mumm.weatherstation.controller.dto.Response;
import mumm.weatherstation.controller.dto.WeatherDto;
import mumm.weatherstation.service.WeatherService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WeatherDataController {

    private final WeatherService weatherService;

    public WeatherDataController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/stations/{id}/weather")
    public Response<List<WeatherDto>> getStationWeatherBatch(
            @PathVariable Long id
    ) {
        return Response.success(this.weatherService.getWeatherData(id));
    }

}

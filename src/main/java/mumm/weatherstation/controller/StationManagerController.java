package mumm.weatherstation.controller;

import mumm.weatherstation.controller.dto.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StationManagerController {

    @GetMapping("station")
    public Response<String> getStations() {
        return new Response<>("get 1 station");
    }

    @GetMapping("stations")
    public Response<String> getStation() {
        return new Response<>("get stations");
    }

    @PutMapping
    public Response<String> insertStation() {
        return new Response<>("insert station");
    }

    @PostMapping
    public Response<String> updateStation() {
        return new Response<>("update station");
    }
}

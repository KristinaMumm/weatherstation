package mumm.weatherstation.controller;

import mumm.weatherstation.controller.dto.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@GetMapping("/health")
	public Response<String> sayHello() {
		return Response.success("Hello World!");
	}

}

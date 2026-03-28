package mumm.weatherstation.client.openmeteo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class OpenMeteoClient {

	private final String baseUrl;

	private final RestTemplate restTemplate;

	public OpenMeteoClient(@Value("${openmeteo.base-url}") String baseUrl, RestTemplate restTemplate) {
		this.baseUrl = baseUrl;
		this.restTemplate = restTemplate;
	}

	public List<WeatherResponse> getWeatherData() {
		String url = this.baseUrl
				+ "?latitude=58.365,41.4036,64.1425&longitude=26.687,2.1744,-21.9266&current=temperature_2m,wind_speed_10m,precipitation&wind_speed_unit=ms";

		ResponseEntity<List<WeatherResponse>> response = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<>() {
				});
		return response.getBody();
	}

}

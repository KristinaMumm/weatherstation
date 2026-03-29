package mumm.weatherstation.client.openmeteo;

import mumm.weatherstation.controller.dto.StationDto;
import mumm.weatherstation.controller.dto.WeatherDto;
import mumm.weatherstation.service.adapter.WeatherAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OpenMeteoWeatherAdapter implements WeatherAdapter {

	private final String baseUrl;

	private final RestTemplate restTemplate;

	private final WeatherMapper weatherMapper;

	public OpenMeteoWeatherAdapter(@Value("${openmeteo.base-url}") String baseUrl, RestTemplate restTemplate,
			WeatherMapper weatherMapper) {
		this.baseUrl = baseUrl;
		this.restTemplate = restTemplate;
		this.weatherMapper = weatherMapper;
	}

	@Override
	public List<WeatherDto> getWeather(List<StationDto> stations) {
		String url = buildUrl(stations);

		List<WeatherResponse> responses = stations.size() == 1 ? List.of(fetchSingle(url)) : fetchMultiple(url);

		if (responses.size() != stations.size()) {
			throw new IllegalStateException(
					"Mismatch: stations=" + stations.size() + ", responses=" + responses.size());
		}

		return mapToDto(stations, responses);
	}

	private WeatherResponse fetchSingle(String url) {
		ResponseEntity<WeatherResponse> response = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<>() {
				});

		return response.getBody();
	}

	private List<WeatherResponse> fetchMultiple(String url) {
		ResponseEntity<List<WeatherResponse>> response = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<>() {
				});

		return response.getBody();
	}

	private String buildUrl(List<StationDto> stations) {
		String latitudes = stations.stream()
			.map(station -> String.valueOf(station.latitude()))
			.collect(Collectors.joining(","));

		String longitudes = stations.stream()
			.map(station -> String.valueOf(station.longitude()))
			.collect(Collectors.joining(","));

		return UriComponentsBuilder.fromUriString(baseUrl)
			.queryParam("latitude", latitudes)
			.queryParam("longitude", longitudes)
			.queryParam("current", String.join(",", CURRENT_PARAMS))
			.queryParam("wind_speed_unit", WIND_SPEED_UNIT)
			.toUriString();
	}

	private List<WeatherDto> mapToDto(List<StationDto> stations, List<WeatherResponse> responses) {
		// TODO : what if Open-Meteo changes order of coordinates?
		List<WeatherDto> result = new java.util.ArrayList<>();

		for (int i = 0; i < stations.size(); i++) {
			result.add(weatherMapper.toDto(stations.get(i), responses.get(i)));
		}

		return result;
	}

	private static final List<String> CURRENT_PARAMS = List.of("temperature_2m", "wind_speed_10m", "precipitation");

	private static final String WIND_SPEED_UNIT = "ms";

}

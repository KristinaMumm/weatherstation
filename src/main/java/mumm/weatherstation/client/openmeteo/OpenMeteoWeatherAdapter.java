package mumm.weatherstation.client.openmeteo;

import mumm.weatherstation.controller.dto.StationDto;
import mumm.weatherstation.controller.dto.WeatherDto;
import mumm.weatherstation.controller.exception.WeatherProviderException;
import mumm.weatherstation.service.adapter.WeatherAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class OpenMeteoWeatherAdapter implements WeatherAdapter {

    private static final List<String> CURRENT_PARAMS = List.of(
            "temperature_2m",
            "wind_speed_10m",
            "precipitation"
    );

    private static final String WIND_SPEED_UNIT = "ms";

    private final String baseUrl;
    private final RestTemplate restTemplate;
    private final WeatherMapper weatherMapper;

    public OpenMeteoWeatherAdapter(
            @Value("${openmeteo.base-url}") String baseUrl,
            RestTemplate restTemplate,
            WeatherMapper weatherMapper
    ) {
        this.baseUrl = baseUrl;
        this.restTemplate = restTemplate;
        this.weatherMapper = weatherMapper;
    }

    @Override
    public List<WeatherDto> getWeather(List<StationDto> stations) {
        String url = buildUrl(stations);
        List<WeatherResponse> responses;

        try {
            responses = stations.size() == 1
                    ? List.of(fetchSingle(url))
                    : fetchMultiple(url);
        } catch (HttpStatusCodeException ex) {
            throw new WeatherProviderException(
                    "Weather provider returned HTTP status " + ex.getStatusCode().value(),
                    ex
            );
        } catch (ResourceAccessException ex) {
            throw new WeatherProviderException(
                    "Weather provider is unreachable or timed out",
                    ex
            );
        }

        validateResponseCount(stations, responses);

        return mapToDto(stations, responses);
    }

    private WeatherResponse fetchSingle(String url) {
        WeatherResponse body = fetchBody(
                url,
                new ParameterizedTypeReference<>() {
                }
        );

        validateWeatherResponse(body);

        return body;
    }

    private List<WeatherResponse> fetchMultiple(String url) {
        List<WeatherResponse> body = fetchBody(
                url,
                new ParameterizedTypeReference<>() {
                }
        );

        if (body.isEmpty()) {
            throw new WeatherProviderException("Weather provider returned empty response");
        }

        body.forEach(this::validateWeatherResponse);

        return body;
    }

    private <T> T fetchBody(String url, ParameterizedTypeReference<T> responseType) {
        ResponseEntity<T> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                responseType
        );

        T body = response.getBody();

        if (body == null) {
            throw new WeatherProviderException("Weather provider returned empty response");
        }

        return body;
    }

    private void validateWeatherResponse(WeatherResponse response) {
        if (response.current() == null) {
            throw new WeatherProviderException("Weather provider returned insufficient data");
        }
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
        // Assumption that Open-Meteo returns results in the same order as requested coordinates.
        return IntStream.range(0, stations.size())
                .mapToObj(i -> weatherMapper.toDto(stations.get(i), responses.get(i)))
                .toList();
    }

    private void validateResponseCount(List<StationDto> stations, List<WeatherResponse> responses) {
        if (responses.size() != stations.size()) {
            throw new WeatherProviderException(
                    "Weather provider returned data for unexpected number of stations: stations="
                            + stations.size()
                            + ", responses="
                            + responses.size()
            );
        }
    }
}
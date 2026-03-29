package mumm.weatherstation.client.openmeteo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record WeatherResponse(double latitude, double longitude, Current current) {

	public WeatherResponse(@JsonProperty("latitude") double latitude, @JsonProperty("longitude") double longitude,
			@JsonProperty("current") Current current) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.current = current;
	}

	public record Current(double temperature, double windSpeed, double precipitation) {

		public Current(@JsonProperty("temperature_2m") double temperature,
				@JsonProperty("wind_speed_10m") double windSpeed, @JsonProperty("precipitation") double precipitation) {
			this.temperature = temperature;
			this.windSpeed = windSpeed;
			this.precipitation = precipitation;
		}

	}

}

package mumm.weatherstation.client.openmeteo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WeatherResponse {

	private final double latitude;

	private final double longitude;

	private final Current current;

	public WeatherResponse(@JsonProperty("latitude") double latitude, @JsonProperty("longitude") double longitude,
			@JsonProperty("current") Current current) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.current = current;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public Current getCurrent() {
		return current;
	}

	public static class Current {

		private final double temperature;

		private final double windSpeed;

		private final double precipitation;

		public Current(@JsonProperty("temperature_2m") double temperature,
				@JsonProperty("wind_speed_10m") double windSpeed, @JsonProperty("precipitation") double precipitation) {
			this.temperature = temperature;
			this.windSpeed = windSpeed;
			this.precipitation = precipitation;
		}

		public double getTemperature() {
			return temperature;
		}

		public double getWindSpeed() {
			return windSpeed;
		}

		public double getPrecipitation() {
			return precipitation;
		}

	}

}
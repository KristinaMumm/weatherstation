package mumm.weatherstation.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "station")
public class Station {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;

	@Column(nullable = false)
	public String name;

	@Column(nullable = false)
	public Double latitude;

	@Column(nullable = false)
	public Double longitude;

	public Station() {
	}

	public Station(Long id, String name, Double latitude, Double longitude) {
		this.id = id;
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
	}

}

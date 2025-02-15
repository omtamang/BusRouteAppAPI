package com.busroute.api.BusRouteAPI.Route;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Stops {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private int stop_id;
	private double lat;
	private double lng;
	private String stop_name;

	@ManyToOne
	@JoinColumn(name = "route_id")
	private Route route;
}

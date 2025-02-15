package com.busroute.api.BusRouteAPI.Route;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Route {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int route_id;
	private String route_name;
	private double start_lat;
	private double start_lng;
	private double end_lat;
	private double end_lng;
	private int no_of_buses;

	@OneToMany(mappedBy = "route")
	private List<Stops> stops;

}

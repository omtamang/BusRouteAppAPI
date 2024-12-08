package com.busroute.api.BusRouteAPI.Route;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

//@Entity
public class Route {
	
	public Route() {
		
	}
	
	@GeneratedValue
	@Id
	private int route_id;
	private String route_name;
	private String start_location;
	private String end_location;
	private int no_of_buses;
	
	
	
}

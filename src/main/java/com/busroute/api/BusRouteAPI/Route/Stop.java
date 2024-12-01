package com.busroute.api.BusRouteAPI.Route;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Stop {
	
	public Stop() {
		
	}
	
	@GeneratedValue
	@Id
	private int stop_id;
	private String location;
	private String stop_name;
	
	public Stop(int stop_id, String location, String stop_name) {
		super();
		this.stop_id = stop_id;
		this.location = location;
		this.stop_name = stop_name;
	}
	
	public int getStop_id() {
		return stop_id;
	}
	public void setStop_id(int stop_id) {
		this.stop_id = stop_id;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getStop_name() {
		return stop_name;
	}
	public void setStop_name(String stop_name) {
		this.stop_name = stop_name;
	}
	
	@Override
	public String toString() {
		return "Stop [stop_id=" + stop_id + ", location=" + location + ", stop_name=" + stop_name + "]";
	}
	
}

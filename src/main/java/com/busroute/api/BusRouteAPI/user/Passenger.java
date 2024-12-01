package com.busroute.api.BusRouteAPI.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Passenger {
	
	public Passenger(){
		
	}
	
	@GeneratedValue
	@Id
	private int passenger_id;
	private String passenger_name;
	private String password;
	
	public Passenger(int passenger_id, String passenger_name, String password) {
		super();
		this.passenger_id = passenger_id;
		this.passenger_name = passenger_name;
		this.password = password;
	}

	public int getPassenger_id() {
		return passenger_id;
	}

	public void setPassenger_id(int passenger_id) {
		this.passenger_id = passenger_id;
	}

	public String getPassenger_name() {
		return passenger_name;
	}

	public void setPassenger_name(String passenger_name) {
		this.passenger_name = passenger_name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Passenger [passenger_id=" + passenger_id + ", passenger_name=" + passenger_name + ", password="
				+ password + "]";
	}
	
	
}

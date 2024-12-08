package com.busroute.api.BusRouteAPI.user;

import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

//@Entity
public class Reminder {
	
	public Reminder() {
		
	}
	
	@GeneratedValue
	@Id
	private int notification_id;
	private LocalTime time;
	
}

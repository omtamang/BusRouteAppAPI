package com.busroute.api.BusRouteAPI.user;

import java.time.LocalTime;

import com.busroute.api.BusRouteAPI.Route.Route;
import com.busroute.api.BusRouteAPI.Route.Stops;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reminder {
	
	@GeneratedValue
	@Id
	private int notification_id;

	private String label;

	@JsonFormat(pattern = "HH:mm")
	private LocalTime time;
	private boolean status;

	@ManyToOne
	private Route notifyRoute;

	@ManyToOne
	private Stops notifyStop;

	@ManyToOne
	@JsonIgnore
	private Passenger notify;
}

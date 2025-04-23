package com.busroute.api.BusRouteAPI.Route;

import com.busroute.api.BusRouteAPI.user.Reminder;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
	@JsonIgnore
	private Route route;

	@JsonIgnore
	@OneToMany(mappedBy = "notifyStop")
	private List<Reminder> reminder;
}

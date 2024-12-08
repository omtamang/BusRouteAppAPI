package com.busroute.api.BusRouteAPI.Resource;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.busroute.api.BusRouteAPI.Repository.PassengerRepository;
import com.busroute.api.BusRouteAPI.user.Passenger;

@Controller
public class PassengerController {
	
	private PassengerRepository passengerRepository;

	public PassengerController(PassengerRepository passengerRepository) {
		super();
		this.passengerRepository = passengerRepository;
	}
	
	@GetMapping("/passengers")
	public List<Passenger> getPassengers() {
		return passengerRepository.findAll();
	}
}

package com.busroute.api.BusRouteAPI.Resource;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.busroute.api.BusRouteAPI.Repository.PassengerRepository;
import com.busroute.api.BusRouteAPI.user.Passenger;

@RestController
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
	
	@PostMapping("/passenger")
	public ResponseEntity<String> addPassenger(@RequestBody Passenger passenger) {
		
		if((boolean)passengerRepository.existsByEmail(passenger.getEmail())) {
			return ResponseEntity.badRequest().body("Email already in use");
		}
		
		String password = passenger.getPassword();
		String hash = passwordEncoder().encode(password);
		passenger.setPassword(hash);
		
		passengerRepository.save(passenger);
		return ResponseEntity.status(HttpStatus.CREATED).body("created successfully");
	}
	
	@GetMapping("/passengers/{email}")
	public List<Passenger> getPassenger(@PathVariable String email){
		return passengerRepository.findByEmail(email);
	}
	
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}

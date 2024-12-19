package com.busroute.api.BusRouteAPI.Resource;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.busroute.api.BusRouteAPI.Repository.PassengerRepository;
import com.busroute.api.BusRouteAPI.user.Passenger;

@RestController
@EnableMethodSecurity
public class PassengerController {
	
	private PassengerRepository passengerRepository;
	private final JwtDecoder jwtDecoder;

	public PassengerController(PassengerRepository passengerRepository, JwtDecoder jwtDecoder) {
		super();
		this.passengerRepository = passengerRepository;
		this.jwtDecoder = jwtDecoder;
	}
	
	@GetMapping("/passengers")
	public List<Passenger> getPassengers() {
		return passengerRepository.findAll();
	}
	
	@PostMapping("/passenger/signup")
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

		
	@GetMapping("/passengers/info")
	public String getPassenger(@RequestHeader("Authorization")String jwt){
		return getEmailFromToken(jwt);
	}
	
	private String getEmailFromToken(String jwt) {
		String token = jwt.substring(7);
		Jwt decodeJwt = (Jwt) jwtDecoder.decode(token); 
		return decodeJwt.getSubject();
	}
	
	@DeleteMapping("/passenger/delete")
	public ResponseEntity<String> deleteUser(@RequestHeader("Authorization")String jwt){
		String email = getEmailFromToken(jwt);
		
		List<Passenger> list = passengerRepository.findByEmail(email);
		
		
		Passenger passenger = list.get(0);
		int id = passenger.getPassenger_id();
		
		passengerRepository.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}
	
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}

package com.busroute.api.BusRouteAPI.Resource;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import com.busroute.api.BusRouteAPI.Repository.EmailVerifyRepository;
import com.busroute.api.BusRouteAPI.Repository.PassengerRepository;
import com.busroute.api.BusRouteAPI.mailing.EmailCode;
import com.busroute.api.BusRouteAPI.mailing.EmailSenderService;
import com.busroute.api.BusRouteAPI.schedule.SheduleDeletionService;
import com.busroute.api.BusRouteAPI.user.Passenger;

@RestController
@EnableMethodSecurity
public class PassengerController {
	
	@Autowired
	private EmailSenderService emailSenderService;
	
	@Autowired
	private EmailVerifyRepository emailVerifyRepository;
	
	@Autowired
	private SheduleDeletionService sheduleDeletionService;
	
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
		
		// generate code
		int code = generateCode();
		String body = code + "";
		
		// save email code in database
		EmailCode emailCode = new EmailCode();
		emailCode.setCode(code);
		emailCode.setPassenger(passenger);
		emailVerifyRepository.save(emailCode);
		
		sheduleDeletionService.scheduleDeletion(passenger.getPassenger_id());
		
		// send email and code to the new user
		emailSenderService.sendEmail(passenger.getEmail(), "This is verification code for NepaGo.", body);
		return ResponseEntity.status(HttpStatus.CREATED).body("created successfully");
	}
	
	private int generateCode() {
		Random rand = new Random();
		int code = rand.nextInt(999999);
		return code;
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

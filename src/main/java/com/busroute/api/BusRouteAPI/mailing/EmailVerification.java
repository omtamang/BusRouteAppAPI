package com.busroute.api.BusRouteAPI.mailing;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.busroute.api.BusRouteAPI.Repository.EmailVerifyRepository;
import com.busroute.api.BusRouteAPI.Repository.PassengerRepository;
import com.busroute.api.BusRouteAPI.user.Passenger;

@RestController
public class EmailVerification {
	
	@Autowired
	private PassengerRepository passengerRepository;
	
	@Autowired
	private EmailVerifyRepository emailVerifyRepository;
	
	@Autowired
	private EmailSenderService emailSenderService;
	
	@PostMapping("/verify/code")
	public ResponseEntity<String> verifyCode(@RequestBody VerificationCode verificationCode){
		
		List<Passenger> passenger = passengerRepository.findByEmail(verificationCode.email());
		Passenger id = passenger.get(0);
		int code = id.getEmailCode().get(0).getCode();
		
		if(String.valueOf(code).equals(verificationCode.code())) {
			id.setVerified(true);
			passengerRepository.save(id);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("Verified");
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong Code");
	}
	
	@GetMapping("/send/code/{email}")
	public ResponseEntity<String> sendLoginCode(@PathVariable String email){
		List<Passenger> passenger = passengerRepository.findByEmail(email);
		
		Passenger pass = passenger.get(0);
		
		// generate code
		int code = generateCode();
		String body = code + "";
		
		if(!passenger.isEmpty()) {
			// save email code in database
			EmailCode emailCode = new EmailCode();
			emailCode.setCode(code);
			emailCode.setPassenger(pass);
			emailVerifyRepository.save(emailCode);
			// send email and code to the new user
			emailSenderService.sendEmail(pass.getEmail(), "This is verification code for NepaGo.", body);
			return ResponseEntity.status(HttpStatus.CREATED).body("created successfully");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found register.");
	}
	
	private int generateCode() {
		Random rand = new Random();
		int code = rand.nextInt(999999);
		return code;
	}
}

record VerificationCode(String email, String code) {}

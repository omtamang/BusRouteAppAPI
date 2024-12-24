package com.busroute.api.BusRouteAPI.jwt;

import java.io.IOException;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.busroute.api.BusRouteAPI.Repository.PassengerRepository;
import com.busroute.api.BusRouteAPI.user.Passenger;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class JwtAuthenticationResource {
	
	private final TokenService tokenService;
	
	private final AuthenticationManager authenticaitonManager;
	
	private final PassengerRepository passengerRepository;
	
	public JwtAuthenticationResource(TokenService tokenService, AuthenticationManager authenticaitonManager, PassengerRepository passengerRepository) {
		super();
		this.tokenService = tokenService;
		this.authenticaitonManager = authenticaitonManager;
		this.passengerRepository = passengerRepository;
	}

	@PostMapping("/token")
	public String token(@RequestBody LoginRequest userLogin) {
		Authentication authentication = authenticaitonManager.authenticate(new UsernamePasswordAuthenticationToken(userLogin.username(), userLogin.password()));
		return tokenService.generateToken(authentication);
	}
	
	public String generateTokenForGoogle(String email, String password) {
		Authentication authentication = authenticaitonManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		return tokenService.generateToken(authentication);
	}
	
	@GetMapping("/")
    public void googleLogin(OAuth2AuthenticationToken token, HttpServletResponse response) throws IOException {
		String email = token.getPrincipal().getAttribute("email");
		String pass = token.getPrincipal().getAttribute("name");
		String hash = passwordEncoder().encode(pass);
		
		if(passengerRepository.existsByEmail(email)) {
			String redirectUrl = "http://localhost:3000/google/handling/" + generateTokenForGoogle(email, pass);
		    response.sendRedirect(redirectUrl);
		}else {
			Passenger passenger = new Passenger();
			String name = email.substring(0, email.indexOf("@"));
			passenger.setEmail(email);
			passenger.setPassenger_name(name);
			passenger.setPassword(hash);
			passengerRepository.save(passenger);

			response.sendRedirect("http://localhost:3000/google/handling/" + generateTokenForGoogle(email, pass));
		}
    }
	
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}

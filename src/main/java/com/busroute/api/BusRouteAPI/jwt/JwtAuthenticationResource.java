package com.busroute.api.BusRouteAPI.jwt;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtAuthenticationResource {
	
	private final TokenService tokenService;
	
	private final AuthenticationManager authenticaitonManager;

	public JwtAuthenticationResource(TokenService tokenService, AuthenticationManager authenticaitonManager) {
		super();
		this.tokenService = tokenService;
		this.authenticaitonManager = authenticaitonManager;
	}

	@PostMapping("/token")
	public String token(@RequestBody LoginRequest userLogin) {
		Authentication authentication = authenticaitonManager.authenticate(new UsernamePasswordAuthenticationToken(userLogin.username(), userLogin.password()));
		return tokenService.generateToken(authentication);
	}
	
}

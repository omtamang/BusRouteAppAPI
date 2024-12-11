package com.busroute.api.BusRouteAPI.Resource;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class SpringSecurityCSRF {
	
	@GetMapping("/csrf-token")
	public CsrfToken retrieveCSRF(HttpServletRequest request) {
		return (CsrfToken) request.getAttribute("_csrf");
	}
}

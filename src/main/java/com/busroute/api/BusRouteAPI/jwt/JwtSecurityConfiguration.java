package com.busroute.api.BusRouteAPI.jwt;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity 
public class JwtSecurityConfiguration {
	
	@Autowired
	DataSource dataSource;

	@Value("${frontend.url}")
	private String frontend;
	
	public JwtSecurityConfiguration(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.cors()
		.and()
		.authorizeHttpRequests(auth -> auth
				.requestMatchers("/token", "/passenger/signup", "/verify/code", "/send/code/{email}", "/api/location").permitAll()
				.anyRequest().authenticated()
				);
		
		http.sessionManagement(
			    session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
			);

		http.oauth2Login(Customizer.withDefaults());
		
		http.oauth2ResourceServer(
				oauth2 -> oauth2.jwt()
				);

		http.csrf().disable();
		return http.build();
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
	    CorsConfiguration configuration = new CorsConfiguration();
	    configuration.addAllowedOrigin(frontend); // Ensure your frontend URL is allowed
	    configuration.addAllowedMethod("*");
	    configuration.addAllowedHeader("*");
	    configuration.setAllowCredentials(true);

	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", configuration);  // Apply the configuration to all routes
	    return source;
	}

	@Bean
	public AuthenticationManager authManager(UserDetailsService userDetailService) {
		var authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return new ProviderManager(authProvider);
	}
	
	@Bean
	public UserDetailsService userDetailsService(DataSource dataSource) {
	    JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

	    // Updated query to match your actual database column names
	    jdbcUserDetailsManager.setUsersByUsernameQuery(
	        "SELECT email AS username, password AS password, verified AS enabled FROM passenger WHERE email = ?"
	    );

	    jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
	        "SELECT email AS username, 'ROLE_USER' AS authority FROM passenger WHERE email = ?"
	    );

	    return jdbcUserDetailsManager;
	}


	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	// To create a key pair using key pair generator
	@Bean
	public KeyPair keyPair() {
		try {
			var keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(2048);
			return keyPairGenerator.generateKeyPair();
		} catch(Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	// Create nimbusds key pair object 
	@Bean
	public RSAKey rsaKey(KeyPair keyPair) {
		return new RSAKey
				.Builder((RSAPublicKey) keyPair.getPublic())
				.privateKey(keyPair.getPrivate())
				.keyID(UUID.randomUUID().toString())
				.build();
	}
	
	// Create JWKSource (JSON Web Key Source)
	@Bean 
	public JWKSource<SecurityContext> jwkSource(RSAKey rsaKey){
		var jwkSet = new JWKSet(rsaKey);
		return (jwkSelector, context) -> jwkSelector.select(jwkSet);
	}
	
	// Decode the RSAKey with the public key
	@Bean
	public JwtDecoder jwtDecoder(RSAKey rsaKey) throws JOSEException {
		return NimbusJwtDecoder
				.withPublicKey(rsaKey.toRSAPublicKey())
				.build();
	}
	
	// Create jwtEncoder
	@Bean
	public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
		return new NimbusJwtEncoder(jwkSource);
	}
}

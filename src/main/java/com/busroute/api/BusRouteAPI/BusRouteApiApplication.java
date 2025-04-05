package com.busroute.api.BusRouteAPI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
@EnableScheduling
public class BusRouteApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BusRouteApiApplication.class, args);
	}

    @Value("${frontend.url}")
    private String FRONTEND;

	@Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin(FRONTEND);
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        source.registerCorsConfiguration("/token", config);
        source.registerCorsConfiguration("/api/location", config);
        System.out.println("Filter triggered");
        return new CorsFilter(source);
    }

}

package com.busroute.api.BusRouteAPI.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.busroute.api.BusRouteAPI.user.Passenger;

public interface PassengerRepository extends JpaRepository<Passenger, Integer> {
	
	List<Passenger> findByEmail(String email);
	boolean existsByEmail(String email);
}

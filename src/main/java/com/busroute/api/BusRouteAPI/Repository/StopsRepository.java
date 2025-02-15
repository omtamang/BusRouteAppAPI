package com.busroute.api.BusRouteAPI.Repository;

import com.busroute.api.BusRouteAPI.Route.Stops;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StopsRepository extends JpaRepository<Stops, Integer> {
}

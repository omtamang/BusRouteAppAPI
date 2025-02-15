package com.busroute.api.BusRouteAPI.Repository;

import com.busroute.api.BusRouteAPI.Route.Route;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteRepository extends JpaRepository<Route, Integer> {

}

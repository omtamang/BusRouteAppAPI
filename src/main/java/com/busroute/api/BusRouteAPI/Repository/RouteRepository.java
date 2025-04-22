package com.busroute.api.BusRouteAPI.Repository;

import com.busroute.api.BusRouteAPI.Bus.Bus;
import com.busroute.api.BusRouteAPI.Route.Route;
import com.busroute.api.BusRouteAPI.Route.Stops;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, Integer> {

    @Query("SELECT r.stops FROM Route r WHERE r.route_id = :routeId")
    List<Stops> findStopbyrouteId(int routeId);

    @Query("SELECT b.bus FROM Route b WHERE b.route_id = :routeId")
    List<Bus> findBusbyrouteId(int routeId);
}

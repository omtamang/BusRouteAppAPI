package com.busroute.api.BusRouteAPI.Resource;

import com.busroute.api.BusRouteAPI.Repository.RouteRepository;
import com.busroute.api.BusRouteAPI.Route.Route;
import com.busroute.api.BusRouteAPI.Route.Stops;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class RouteController {

    @Autowired
    private RouteRepository routeRepository;

    @GetMapping("/get-route")
    public List<Route> getRoutes(){
        return routeRepository.findAll();
    }

    @GetMapping("/get-route/{routeId}")
    public Optional<Route> getRouteById(@PathVariable int routeId){
        return routeRepository.findById(routeId);
    }

    @GetMapping("/get-route/{routeId}/stops")
    public List<Stops> getStopfromid(@PathVariable int routeId){
        return routeRepository.findStopbyrouteId(routeId);
    }

    @PostMapping("/add-route")
    public ResponseEntity<Route> addRoute(@RequestBody Route route){
        routeRepository.save(route);
        return ResponseEntity.status(201).body(route);
    }
}

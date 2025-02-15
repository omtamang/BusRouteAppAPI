package com.busroute.api.BusRouteAPI.Resource;

import com.busroute.api.BusRouteAPI.Repository.RouteRepository;
import com.busroute.api.BusRouteAPI.Route.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RouteController {

    @Autowired
    private RouteRepository routeRepository;

    @GetMapping("/get-route")
    public List<Route> getRoutes(){
        return routeRepository.findAll();
    }

    @PostMapping("/add-route")
    public ResponseEntity<Route> addRoute(@RequestBody Route route){
        routeRepository.save(route);
        return ResponseEntity.status(201).body(route);
    }
}

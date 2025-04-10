package com.busroute.api.BusRouteAPI.Resource;

import com.busroute.api.BusRouteAPI.Bus.Bus;
import com.busroute.api.BusRouteAPI.Repository.RouteRepository;
import com.busroute.api.BusRouteAPI.Route.Route;
import com.busroute.api.BusRouteAPI.Route.Stops;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
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

    @GetMapping("/get-route/{routeId}/buses")
    public List<Bus> getBusById(@PathVariable int routeId){
        return routeRepository.findBusbyrouteId(routeId);
    }

    @PostMapping("/add-route")
    public ResponseEntity<Route> addRoute(@RequestBody Route route){
        routeRepository.save(route);
        return ResponseEntity.status(201).body(route);
    }

    @DeleteMapping("/route/delete/{routeId}")
    public ResponseEntity<Integer> deleteRoute(@PathVariable int routeId){
        routeRepository.deleteById(routeId);
        return ResponseEntity.status(204).body(routeId);
    }

    @PutMapping("/route/{routeId}")
    public ResponseEntity<Route> updateRoute(@PathVariable int routeId, @RequestBody Route updatedRoute){
        Optional<Route> existingRoute = routeRepository.findById(routeId);

        if(existingRoute.isPresent()){
            Route route = existingRoute.get();
            route.setRoute_name(updatedRoute.getRoute_name());
            route.setStart_lat(updatedRoute.getStart_lat());
            route.setStart_lng(updatedRoute.getStart_lng());
            route.setEnd_lat(updatedRoute.getEnd_lat());
            route.setEnd_lng(updatedRoute.getEnd_lng());
            route.setNo_of_buses(updatedRoute.getNo_of_buses());
            route.setStops(updatedRoute.getStops());

            routeRepository.save(route);
            return ResponseEntity.ok(route);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

}

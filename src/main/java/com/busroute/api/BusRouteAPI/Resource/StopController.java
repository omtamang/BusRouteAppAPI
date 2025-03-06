package com.busroute.api.BusRouteAPI.Resource;

import com.busroute.api.BusRouteAPI.Repository.RouteRepository;
import com.busroute.api.BusRouteAPI.Repository.StopsRepository;
import com.busroute.api.BusRouteAPI.Route.Route;
import com.busroute.api.BusRouteAPI.Route.Stops;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class StopController {

    @Autowired
    StopsRepository stopsRepository;

    @Autowired
    RouteRepository routeRepository;

    @GetMapping("/get-stops")
    public List<Stops> getAllStops(){
        return stopsRepository.findAll();
    }

    @PostMapping("/add-stops")
    public ResponseEntity<Stops> addStop(@RequestBody Stops stops){
        if(stops.getRoute() == null){
            return ResponseEntity.badRequest().body(null);
        }

        int id = stops.getRoute().getRoute_id();

        Route route = routeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Route not found with ID: " + id));

        stops.setRoute(route);

        Stops save = stopsRepository.save(stops);

        return ResponseEntity.status(HttpStatus.CREATED).body(save);
    }

    @DeleteMapping("/stop/delete/{stopId}")
    public ResponseEntity<Integer> deleteStopById(@PathVariable int stopId){
        stopsRepository.deleteById(stopId);
        return ResponseEntity.status(204).body(stopId);
    }
}

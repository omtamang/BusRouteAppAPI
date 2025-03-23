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

    @PostMapping("/add-stops/{routeId}")
    public ResponseEntity<Stops> addStop(@RequestBody Stops stops, @PathVariable int routeId){

        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new RuntimeException("Route not found with ID: " + routeId));

        stops.setRoute(route);

        Stops save = stopsRepository.save(stops);

        return ResponseEntity.status(HttpStatus.CREATED).body(save);
    }

    @DeleteMapping("/stop/delete/{stopId}")
    public ResponseEntity<Integer> deleteStopById(@PathVariable int stopId){
        stopsRepository.deleteById(stopId);
        return ResponseEntity.status(204).body(stopId);
    }

    @PutMapping("/stop/{stopId}/update/{routeId}")
    public ResponseEntity<Stops> updateStopById(@PathVariable int stopId, @PathVariable int routeId, @RequestBody Stops updatedStop){
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new RuntimeException("Route not found with ID: " + routeId));

        Optional<Stops> optionalStop = stopsRepository.findById(stopId);

        if(optionalStop.isPresent()){
            Stops stop = optionalStop.get();
            stop.setStop_name(updatedStop.getStop_name());
            stop.setLat(updatedStop.getLat());
            stop.setLng(updatedStop.getLng());
            stop.setRoute(route);

            stopsRepository.save(stop);
            return ResponseEntity.ok().body(stop);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
}

package com.busroute.api.BusRouteAPI.Resource;

import com.busroute.api.BusRouteAPI.Bus.Bus;
import com.busroute.api.BusRouteAPI.Repository.BusRespository;
import com.busroute.api.BusRouteAPI.Repository.RouteRepository;
import com.busroute.api.BusRouteAPI.Route.Route;
import com.busroute.api.BusRouteAPI.Route.Stops;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class BusController {


    private final BusRespository busRespository;


    private final RouteRepository routeRepository;

    @GetMapping("/get-bus")
    public List<Bus> getAllBus(){
        return busRespository.findAll();
    }

    @PostMapping("/add-bus/{routeId}")
    @PreAuthorize("@accessService.isAdmin(authentication.name)")
    public ResponseEntity<String> addBus(@RequestBody Bus bus, @PathVariable int routeId){
        if((boolean) busRespository.existsByDeviceId(bus.getDeviceId())){
            return ResponseEntity.badRequest().body("Bus already present in the route.");
        }
        else {
            Route route = routeRepository.findById(routeId)
                    .orElseThrow(() -> new RuntimeException("Route not found with ID: " + routeId));

            bus.setBusRoute(route);
            busRespository.save(bus);
            return ResponseEntity.status(HttpStatusCode.valueOf(200)).body("Created Successfully");
        }
    }

    @DeleteMapping("/bus/delete/{busId}")
    @PreAuthorize("@accessService.isAdmin(authentication.name)")
    public ResponseEntity<String> deleteBusById(@PathVariable int busId){
        Optional<Bus> bus = busRespository.findById(busId);

        if (bus.isPresent()) {
            busRespository.deleteById(busId);
            return ResponseEntity.noContent().build();  // 204 No Content
        } else {
            return ResponseEntity.notFound().build();  // 404 Not Found if bus doesn't exist
        }
    }

    @PutMapping("/bus/{busId}/update/{routeId}")
    @PreAuthorize("@accessService.isAdmin(authentication.name)")
    public ResponseEntity<Bus> updateBusById(@PathVariable int busId, @PathVariable int routeId, @RequestBody Bus updatedBus){
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new RuntimeException("Route not found with ID: " + routeId));

        Optional<Bus> optionalBus = busRespository.findById(busId);

        if(optionalBus.isPresent()){
            Bus bus = optionalBus.get();
            bus.setBusNo(updatedBus.getBusNo());
            bus.setDeviceId(updatedBus.getDeviceId());
            bus.setStatus(updatedBus.isStatus());
            bus.setBusRoute(route);

            busRespository.save(bus);
            return ResponseEntity.ok().body(bus);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
}

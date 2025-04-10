package com.busroute.api.BusRouteAPI.Resource;

import com.busroute.api.BusRouteAPI.Bus.Bus;
import com.busroute.api.BusRouteAPI.Repository.BusRespository;
import com.busroute.api.BusRouteAPI.Repository.RouteRepository;
import com.busroute.api.BusRouteAPI.Route.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class BusController {

    @Autowired
    private BusRespository busRespository;

    @Autowired
    private RouteRepository routeRepository;

    @GetMapping("/get-bus")
    public List<Bus> getAllBus(){
        return busRespository.findAll();
    }

    @PostMapping("/add-bus")
    public ResponseEntity<String> addBus(@RequestBody Bus bus){
        if((boolean) busRespository.existsByDeviceId(bus.getDeviceId())){
            return ResponseEntity.badRequest().body("Bus already present in the route.");
        }
        else {
            busRespository.save(bus);
            return ResponseEntity.status(HttpStatusCode.valueOf(200)).body("Created Successfully");
        }
    }

    @DeleteMapping("/bus/delete/{busId}")
    public ResponseEntity<String> deleteBusById(@PathVariable int busId){
        Optional<Bus> bus = busRespository.findById(busId);

        if (bus.isPresent()) {
            busRespository.deleteById(busId);
            return ResponseEntity.noContent().build();  // 204 No Content
        } else {
            return ResponseEntity.notFound().build();  // 404 Not Found if bus doesn't exist
        }
    }
}

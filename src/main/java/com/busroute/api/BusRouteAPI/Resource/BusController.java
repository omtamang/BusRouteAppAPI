package com.busroute.api.BusRouteAPI.Resource;

import com.busroute.api.BusRouteAPI.Bus.Bus;
import com.busroute.api.BusRouteAPI.Repository.BusRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class BusController {

    @Autowired
    private BusRespository busRespository;

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
}

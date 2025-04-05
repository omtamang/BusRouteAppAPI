package com.busroute.api.BusRouteAPI.Resource;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/location")
    public class LocationController {

    private double latitude;
    private double longitude;

    @PostMapping
    public Map<String, String> receiveLocation(@RequestBody Map<String, Double> locationData) {
        System.out.println("Received location: " + locationData);

        this.latitude = locationData.get("latitude");
        this.longitude = locationData.get("longitude");

        Map<String, String> response = new HashMap<>();
        response.put("message", "Location updated successfully");
        return response;
    }

    @GetMapping
    public Map<String, Double> getLocation() {
        Map<String, Double> response = new HashMap<>();
        response.put("latitude", latitude);
        response.put("longitude", longitude);

        System.out.println(latitude);
        System.out.println("--------++++++++++--------");
        System.out.println(longitude);
        return response;
    }

}


package com.busroute.api.BusRouteAPI.Resource;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/location")
public class LocationController {

    // Stores deviceId -> (latitude, longitude)
    private Map<String, Map<String, Double>> locationMap = new HashMap<>();

    // Receive location from Android app
    @PostMapping
    public Map<String, String> receiveLocation(@RequestBody Map<String, Object> locationData) {
        String deviceId = (String) locationData.get("deviceId");
        Double latitude = ((Number) locationData.get("latitude")).doubleValue();
        Double longitude = ((Number) locationData.get("longitude")).doubleValue();

        System.out.println("Received from device: " + deviceId + " -> Lat: " + latitude + ", Lng: " + longitude);

        // Store in map
        Map<String, Double> coords = new HashMap<>();
        coords.put("latitude", latitude);
        coords.put("longitude", longitude);
        locationMap.put(deviceId, coords);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Location updated successfully for device: " + deviceId);
        return response;
    }

    // Retrieve location for a specific device
    @GetMapping("/{deviceId}")
    public Map<String, Double> getLocation(@PathVariable String deviceId) {
        Map<String, Double> coords = locationMap.get(deviceId);
        if (coords == null) {
            throw new RuntimeException("Device ID not found");
        }

        System.out.println("Fetched for " + deviceId + " -> " + coords);
        return coords;
    }
}

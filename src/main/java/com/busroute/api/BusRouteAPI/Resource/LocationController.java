package com.busroute.api.BusRouteAPI.Resource;

import com.busroute.api.BusRouteAPI.Bus.Bus;
import com.busroute.api.BusRouteAPI.Repository.BusRespository;
import com.busroute.api.BusRouteAPI.Route.Stops;
import com.busroute.api.BusRouteAPI.Route.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/location")
public class LocationController {

    @Autowired
    private BusRespository busRespository;

    // Stores deviceId -> (latitude, longitude)
    private Map<String, Map<String, Double>> locationMap = new HashMap<>();

    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of the earth in km
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; // in kilometers
    }

    // Receive location from Android app
    @PostMapping
    public Map<String, String> receiveLocation(@RequestBody Map<String, Object> locationData) {
        String deviceId = (String) locationData.get("deviceId");
        double latitude = ((Number) locationData.get("latitude")).doubleValue();
        double longitude = ((Number) locationData.get("longitude")).doubleValue();

        Optional<Bus> existingBus = busRespository.findByDeviceId(deviceId);

        if (existingBus.isPresent()) {
            Bus bus = existingBus.get();
            LocalDateTime now = LocalDateTime.now();
            double speed = 0;

            if (bus.getLastUpdated() != null) {
                Duration duration = Duration.between(bus.getLastUpdated(), now);
                long seconds = duration.getSeconds();

                if (seconds > 0) {
                    double distance = calculateDistance(bus.getLatitude(), bus.getLongitude(), latitude, longitude);
                    speed = (distance / (seconds / 3600.0)); // km/h
                }
            }

            bus.setDeviceId(deviceId);
            bus.setLatitude(latitude);
            bus.setLongitude(longitude);
            bus.setSpeed(speed);
            bus.setLastUpdated(now);
            bus.setStatus(true);

            // === Find Next Stop ===
            Route route = bus.getBusRoute();
            Stops nextStop = null;
            double minDistance = Double.MAX_VALUE;

            if (route != null && route.getStops() != null) {
                for (Stops stop : route.getStops()) {
                    double distanceToStop = calculateDistance(latitude, longitude, stop.getLat(), stop.getLng());

                    if (distanceToStop < minDistance) {
                        minDistance = distanceToStop;
                        nextStop = stop;
                    }
                }
            }

            if (nextStop != null && speed > 0) {
                double etaHours = minDistance / speed;
                long etaSeconds = (long) (etaHours * 3600);
                LocalDateTime arrivalTime = now.plusSeconds(etaSeconds);

                bus.setNextStop(nextStop.getStop_name());
                bus.setApproximate_arrival_time(etaSeconds); // You can change this to formatted time if needed
            }

            busRespository.save(bus);
        }

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


}

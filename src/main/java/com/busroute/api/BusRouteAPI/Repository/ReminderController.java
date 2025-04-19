package com.busroute.api.BusRouteAPI.Repository;

import com.busroute.api.BusRouteAPI.Route.Route;
import com.busroute.api.BusRouteAPI.Route.Stops;
import com.busroute.api.BusRouteAPI.user.Passenger;
import com.busroute.api.BusRouteAPI.user.Reminder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ReminderController {

    @Autowired
    private BusRespository busRespository;
    
    @Autowired
    private RouteRepository routeRepository;
    
    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private  StopsRepository stopsRepository;

    @Autowired
    private ReminderRepository reminderRepository;

    @GetMapping("/getNotification/{email}")
    public List<Reminder> getAllNotification(@PathVariable String email){
        return reminderRepository.findByNotifyEmail(email);
    }

    @PostMapping("/setReminder/{routeId}/{stopId}/{email}")
    public ResponseEntity<Reminder> setReminder(@PathVariable int routeId, @PathVariable int stopId, @PathVariable String email, @RequestBody Reminder reminder){
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new RuntimeException("Route not found with ID: " + routeId));

        List<Passenger> passengerList = passengerRepository.findByEmail(email);
        if(passengerList.isEmpty()){
            throw new RuntimeException("Passenger not found with email: " + email);
        }

        Passenger passenger = passengerList.get(0);

        Stops stops = stopsRepository.findById(stopId)
                .orElseThrow(() -> new RuntimeException("Stop not found with ID: " + stopId));

        Reminder newReminder = new Reminder();
        newReminder.setLabel(reminder.getLabel());
        newReminder.setTime(reminder.getTime());
        newReminder.setStatus(true);
        newReminder.setNotify(passenger);
        newReminder.setNotifyRoute(route);
        newReminder.setNotifyStop(stops);

        Reminder savedReminder = reminderRepository.save(newReminder);

        return ResponseEntity.ok(savedReminder);
    }

    @PutMapping("/updateReminder/{routeId}/{stopId}/{email}/{notificationId}")
    public ResponseEntity<Reminder> updateReminder(@PathVariable int routeId, @PathVariable int stopId, @PathVariable String email, @PathVariable int notificationId, @RequestBody Reminder reminder){
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new RuntimeException("Route not found with ID: " + routeId));

        List<Passenger> passengerList = passengerRepository.findByEmail(email);
        if(passengerList.isEmpty()){
            throw new RuntimeException("Passenger not found with email: " + email);
        }
        Passenger passenger = passengerList.get(0);

        Stops stops = stopsRepository.findById(stopId)
                .orElseThrow(() -> new RuntimeException("Stop not found with ID: " + stopId));

        Optional<Reminder> existingReminder = reminderRepository.findById(notificationId);

        if(existingReminder.isPresent()){
            Reminder newReminder = existingReminder.get();
            newReminder.setLabel(reminder.getLabel());
            newReminder.setTime(reminder.getTime());
            newReminder.setStatus(reminder.isStatus());
            newReminder.setNotify(passenger);
            newReminder.setNotifyRoute(route);
            newReminder.setNotifyStop(stops);

            Reminder savedReminder = reminderRepository.save(newReminder);

            return ResponseEntity.ok(savedReminder);
        }else {
            throw new RuntimeException("Unable to set notification");
        }
    }

    @DeleteMapping("/deleteRemider/{reminderId}")
    public ResponseEntity<String> deleteReminder(@PathVariable int reminderId){
        reminderRepository.deleteById(reminderId);
        return  ResponseEntity.ok("Deleted reminder with Id " + reminderId);
    }
}

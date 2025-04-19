package com.busroute.api.BusRouteAPI.Repository;

import com.busroute.api.BusRouteAPI.Bus.Bus;
import com.busroute.api.BusRouteAPI.Route.Route;
import com.busroute.api.BusRouteAPI.Route.Stops;
import com.busroute.api.BusRouteAPI.mailing.EmailSenderService;
import com.busroute.api.BusRouteAPI.user.Passenger;
import com.busroute.api.BusRouteAPI.user.Reminder;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReminderService {

    private final ReminderRepository reminderRepository;
    private final BusRespository busRespository;
    private final EmailSenderService emailSenderService;

    @Scheduled(fixedRate = 60000) // runs every 60 seconds
    public void checkAndSendReminders() {
        LocalTime now = LocalTime.now().withSecond(0).withNano(0);

        List<Reminder> dueReminders = reminderRepository.findByTime(now);

        for (Reminder reminder : dueReminders) {
            Route route = reminder.getNotifyRoute();
            Stops stop = reminder.getNotifyStop();
            Passenger passenger = reminder.getNotify();

            List<Bus> busesOnRoute = busRespository.findByBusRoute(route);

            for (Bus bus : busesOnRoute) {
                if (bus.isStatus() && isBusEligible(bus, stop)) {
                    emailSenderService.sendReminderEmail(passenger.getEmail(), route.getRoute_name(), stop.getStop_name());
                    break; // avoid multiple notifications for same reminder
                }
            }
        }
    }

    private boolean isBusEligible(Bus bus, Stops stop) {
        double distance = calculateDistance(bus.getLatitude(), bus.getLongitude(), stop.getLat(), stop.getLng());
        if (distance <= 1.0) return true;

        if (bus.getApproximate_arrival_time() <= 15.0) return true;

        return false;
    }

    // Haversine formula
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of the Earth in km
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}

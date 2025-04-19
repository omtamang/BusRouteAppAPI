package com.busroute.api.BusRouteAPI.Repository;

import com.busroute.api.BusRouteAPI.user.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;
import java.util.List;

public interface ReminderRepository extends JpaRepository<Reminder, Integer> {
    List<Reminder> findByTime(LocalTime time);
    List<Reminder> findByNotifyEmail(String email);
    boolean existsByNotifyEmailAndTime(String email, LocalTime time);
}

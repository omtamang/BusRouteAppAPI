package com.busroute.api.BusRouteAPI.schedule;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import com.busroute.api.BusRouteAPI.Repository.EmailVerifyRepository;
import com.busroute.api.BusRouteAPI.Repository.PassengerRepository;
import com.busroute.api.BusRouteAPI.mailing.EmailCode;
import com.busroute.api.BusRouteAPI.user.Passenger;

import jakarta.transaction.Transactional;

@Service
@EnableScheduling
public class SheduleDeletionService {
	
	@Autowired
	private EmailVerifyRepository emailVerifyRepository;
	
	@Autowired
	private PassengerRepository passengerRepository;
	
	@Autowired
    private TaskScheduler taskScheduler; 
	
	@Transactional
	public void scheduleDeletion(int id) {
		taskScheduler.schedule(() -> {
            Optional<Passenger> passengerOptional = passengerRepository.findById(id);
            if (passengerOptional.isPresent()) {
                Passenger passenger = passengerOptional.get();
                List<EmailCode> emailCodes = passenger.getEmailCode();
                if (!emailCodes.isEmpty()) {
                    emailVerifyRepository.deleteAll(emailCodes);
                    System.out.println("Code expired and deleted for passenger ID: " + id);
                } else {
                    System.out.println("Code already expired or does not exist for passenger ID: " + id);
                }
            } else {
                System.out.println("Passenger not found with ID: " + id);
            }
        }, new java.util.Date(System.currentTimeMillis() + 1 * 60 * 1000)); // Delay of 3 minutes
    }
}

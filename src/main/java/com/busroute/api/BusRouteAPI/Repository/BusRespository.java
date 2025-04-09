package com.busroute.api.BusRouteAPI.Repository;

import com.busroute.api.BusRouteAPI.Bus.Bus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusRespository extends JpaRepository<Bus, Integer> {

    boolean existsByDeviceId(String deviceId);

}

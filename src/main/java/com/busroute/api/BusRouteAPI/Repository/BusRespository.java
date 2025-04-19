package com.busroute.api.BusRouteAPI.Repository;

import com.busroute.api.BusRouteAPI.Bus.Bus;
import com.busroute.api.BusRouteAPI.Route.Route;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BusRespository extends JpaRepository<Bus, Integer> {

    boolean existsByDeviceId(String deviceId);
    Optional<Bus> findByDeviceId(String deviceId);
    List<Bus> findByBusRoute(Route route);

}

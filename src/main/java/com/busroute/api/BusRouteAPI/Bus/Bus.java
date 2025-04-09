package com.busroute.api.BusRouteAPI.Bus;

import com.busroute.api.BusRouteAPI.Route.Route;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bus {

    @Id
    @GeneratedValue
    private int busId;
    private String deviceId;
    private String busNo;
    private double latitude;
    private double longitude;
    private double speed;
    private String next_stop;
    private boolean status;
    private double approximate_arrival_time;

    @ManyToOne
    private Route bus_route;
}

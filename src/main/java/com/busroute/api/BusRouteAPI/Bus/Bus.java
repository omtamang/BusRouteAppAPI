package com.busroute.api.BusRouteAPI.Bus;

import com.busroute.api.BusRouteAPI.Route.Route;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    private String nextStop;
    private boolean status;
    private double approximate_arrival_time;
    private LocalDateTime lastUpdated;

    @ManyToOne
    @JoinColumn(name = "route_id")
    @JsonIgnore
    private Route busRoute;
}

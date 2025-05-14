package com.busroute.api.BusRouteAPI.jwt;

import org.springframework.stereotype.Service;

@Service("accessService")
public class AccessService {

    public boolean isAdmin(String email){
        return email.equals("james@gmail.com");
    }
}

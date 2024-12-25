package com.busroute.api.BusRouteAPI.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.busroute.api.BusRouteAPI.mailing.EmailCode;

public interface EmailVerifyRepository extends JpaRepository<EmailCode, Long>{

}

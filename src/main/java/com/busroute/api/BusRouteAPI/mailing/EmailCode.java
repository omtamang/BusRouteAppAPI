package com.busroute.api.BusRouteAPI.mailing;

import com.busroute.api.BusRouteAPI.user.Passenger;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "verify")
public class EmailCode {
	
	public EmailCode() {
		
	}
	
	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
	@JoinColumn(name ="passenger_id")
	private Passenger passenger;
	
	private int code;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Passenger getPassenger() {
		return passenger;
	}

	public void setPassenger(Passenger passenger) {
		this.passenger = passenger;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "EmailCode [id=" + id + ", passenger=" + passenger + ", code=" + code + "]";
	}
	
}

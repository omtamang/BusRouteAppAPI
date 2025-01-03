package com.busroute.api.BusRouteAPI.user;

import java.util.List;

import com.busroute.api.BusRouteAPI.mailing.EmailCode;

import jakarta.persistence.*;

@Entity
public class Passenger {
	
	public Passenger() {

	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int passenger_id;

	private String passenger_name;
	private String email;
	private String password;
	private boolean verified;

	@OneToMany(mappedBy = "passenger", fetch = FetchType.EAGER )
	private List<EmailCode> emailCode;

	public Passenger(int passenger_id, String passenger_name, String email, String password, boolean verified,
			List<EmailCode> emailCode) {
		super();
		this.passenger_id = passenger_id;
		this.passenger_name = passenger_name;
		this.email = email;
		this.password = password;
		this.verified = verified;
		this.emailCode = emailCode;
	}

	public int getPassenger_id() {
		return passenger_id;
	}

	public void setPassenger_id(int passenger_id) {
		this.passenger_id = passenger_id;
	}

	public String getPassenger_name() {
		return passenger_name;
	}

	public void setPassenger_name(String passenger_name) {
		this.passenger_name = passenger_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public List<EmailCode> getEmailCode() {
		return emailCode;
	}

	public void setEmailCode(List<EmailCode> emailCode) {
		this.emailCode = emailCode;
	}

	@Override
	public String toString() {
		return "Passenger [passenger_id=" + passenger_id + ", passenger_name=" + passenger_name + ", email=" + email
				+ ", password=" + password + ", verified=" + verified + ", emailCode=" + emailCode + "]";
	}
		
}

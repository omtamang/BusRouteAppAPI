package com.busroute.api.BusRouteAPI.user;

import java.util.List;

import com.busroute.api.BusRouteAPI.mailing.EmailCode;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Passenger {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int passenger_id;

	private String passenger_name;
	private String email;
	private String password;
	private boolean verified;

	@OneToMany(mappedBy = "passenger", fetch = FetchType.EAGER )
	private List<EmailCode> emailCode;

	@OneToMany(mappedBy = "notify")
	private List<Reminder> reminder;
		
}

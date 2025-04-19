package com.busroute.api.BusRouteAPI.mailing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

	@Autowired
	private JavaMailSender mailSender;

	private static final String FROM_EMAIL = "tamangom818@gmail.com";

	public void sendReminderEmail(String toEmail, String routeName, String stopName) {
		String subject = "🚌 Bus Reminder: Your bus is arriving soon!";
		String body = String.format(
				"Hello,\n\nYour bus on route '%s' is approaching the stop '%s'.\n"
						+ "It will arrive within 15 minutes or is very close (less than 1 km).\n\n"
						+ "Please be ready.\n\n- Bus Safar Team", routeName, stopName);

		sendEmail(toEmail, subject, body);
	}

	public void sendEmail(String toEmail, String subject, String body) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(FROM_EMAIL);
		message.setTo(toEmail);
		message.setSubject(subject);
		message.setText(body);

		mailSender.send(message);
		System.out.println("Mail sent successfully to " + toEmail);
	}
}

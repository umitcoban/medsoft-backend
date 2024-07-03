package com.ucoban.medsoft.appointmentserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AppointmentServerApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(AppointmentServerApplication.class, args);
	}
	
}

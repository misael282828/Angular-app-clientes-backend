package com.angular.backend.angular_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@EnableAutoConfiguration
public class AngularBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(AngularBackendApplication.class, args);
	}

}

package com.polis.polishospital;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.polis.polishospital")
public class PolishospitalApplication {

	public static void main(String[] args) {
		SpringApplication.run(PolishospitalApplication.class, args);
	}

}

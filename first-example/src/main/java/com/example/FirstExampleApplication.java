package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication(scanBasePackages = {"com.github.xdamah", "com.example"})
public class FirstExampleApplication {
	public static void main(String[] args) {
		SpringApplication.run(FirstExampleApplication.class, args);
	}

}
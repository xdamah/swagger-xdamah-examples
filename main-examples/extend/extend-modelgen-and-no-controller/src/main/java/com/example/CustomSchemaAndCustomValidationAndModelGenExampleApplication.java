package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "io.github.xdamah", "com.example" })
public class CustomSchemaAndCustomValidationAndModelGenExampleApplication {
	
	public static void main(String[] args) {

		SpringApplication.run(CustomSchemaAndCustomValidationAndModelGenExampleApplication.class, args);
		
	}

}
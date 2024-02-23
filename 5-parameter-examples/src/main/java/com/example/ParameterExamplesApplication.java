package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;



@SpringBootApplication(scanBasePackages = { "io.github.xdamah", "com.example" })
public class ParameterExamplesApplication {
	public static void main(String[] args) {

		SpringApplication.run(ParameterExamplesApplication.class, args);
	}

	

}
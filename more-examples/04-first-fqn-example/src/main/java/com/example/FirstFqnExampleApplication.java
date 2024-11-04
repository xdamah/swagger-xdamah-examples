package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "io.github.xdamah", "com.example" })
public class FirstFqnExampleApplication {

	public static void main(String[] args) {

		SpringApplication.run(FirstFqnExampleApplication.class, args);
		
	}

}
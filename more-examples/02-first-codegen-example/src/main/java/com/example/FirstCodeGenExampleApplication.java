package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "io.github.xdamah", "com.example" })
public class FirstCodeGenExampleApplication {
	

	public static void main(String[] args) {

		SpringApplication.run(FirstCodeGenExampleApplication.class, args);
		
	}
	

}
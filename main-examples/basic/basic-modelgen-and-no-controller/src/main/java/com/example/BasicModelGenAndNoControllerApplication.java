package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "io.github.xdamah", "com.example" })
public class BasicModelGenAndNoControllerApplication {
	
	public static void main(String[] args) {

		SpringApplication.run(BasicModelGenAndNoControllerApplication.class, args);
			
	}

}
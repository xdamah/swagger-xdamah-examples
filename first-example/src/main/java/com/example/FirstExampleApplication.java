package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication(scanBasePackages = {"com.github.xdamah", "com.example"})
public class FirstExampleApplication {
	private static final Logger logger = LoggerFactory.getLogger(FirstExampleApplication.class);
	public static void main(String[] args) {
		
		SpringApplication.run(FirstExampleApplication.class, args);
		logger.info("info message on startup");
		logger.debug("debug message on startup");
		logger.warn("warn message on startup");
		logger.error("error message on startup");
	}

}
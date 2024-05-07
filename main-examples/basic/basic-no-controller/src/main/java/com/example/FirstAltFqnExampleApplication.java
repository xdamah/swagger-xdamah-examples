package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.xdamah.modelconverter.ByteArrayPropertyConverter;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.core.jackson.TypeNameResolver;
import jakarta.annotation.PostConstruct;

@SpringBootApplication(scanBasePackages = { "io.github.xdamah", "com.example" })
public class FirstAltFqnExampleApplication {
	private static final Logger logger = LoggerFactory.getLogger(FirstAltFqnExampleApplication.class);
	
	
	

	public static void main(String[] args) {
		SpringApplication.run(FirstAltFqnExampleApplication.class, args);
		
		logger.info("info message on startup");
		logger.debug("debug message on startup");
		logger.warn("warn message on startup");
		logger.error("error message on startup");
	}
	

@PostConstruct

	  void adjustModelConverters() {
	
		TypeNameResolver.std.setUseFqn(true);
		ModelConverters.getInstance().addConverter(new ByteArrayPropertyConverter());
		
	}

}
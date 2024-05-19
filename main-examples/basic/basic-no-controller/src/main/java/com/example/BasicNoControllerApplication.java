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
public class BasicNoControllerApplication {
	private static final Logger logger = LoggerFactory.getLogger(BasicNoControllerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(BasicNoControllerApplication.class, args);
	}

	@PostConstruct
	void adjustModelConverters() {
		TypeNameResolver.std.setUseFqn(true);
		ModelConverters.getInstance().addConverter(new ByteArrayPropertyConverter());

	}

}
package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.xdamah.modelconverter.SubTypedPropertyConverter;
import io.swagger.v3.core.converter.ModelConverters;
import jakarta.annotation.PostConstruct;

@SpringBootApplication(scanBasePackages = { "io.github.xdamah", "com.example" })
public class PolymorphismAlt1ExampleApplication {
	public static void main(String[] args) {

		SpringApplication.run(PolymorphismAlt1ExampleApplication.class, args);
	}
	
	@PostConstruct

	void adjustModelConverters() {
		ModelConverters.getInstance().addConverter(new SubTypedPropertyConverter());
		
	}

}
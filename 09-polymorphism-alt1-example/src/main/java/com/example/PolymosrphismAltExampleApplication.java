package com.example;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.xdamah.modelconverter.ByteArrayPropertyConverter;
import io.github.xdamah.modelconverter.SubTypedPropertyConverter;
import io.swagger.v3.core.converter.ModelConverter;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.core.jackson.ModelResolver;
import jakarta.annotation.PostConstruct;

@SpringBootApplication(scanBasePackages = { "io.github.xdamah", "com.example" })
public class PolymosrphismAltExampleApplication {
	public static void main(String[] args) {

		SpringApplication.run(PolymosrphismAltExampleApplication.class, args);
	}
	
	@PostConstruct

	void adjustModelConverters() {
		ModelConverters.getInstance().addConverter(new SubTypedPropertyConverter());
		
	}

}
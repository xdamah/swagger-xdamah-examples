package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.xdamah.modelconverter.SubTypedPropertyConverter;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.core.jackson.TypeNameResolver;
import jakarta.annotation.PostConstruct;

@SpringBootApplication(scanBasePackages = { "io.github.xdamah", "com.example" })
public class PolymorphicNoControllerExampleApplication {
	public static void main(String[] args) {
		
		SpringApplication.run(PolymorphicNoControllerExampleApplication.class, args);
	}
	@PostConstruct
	void init()
	{
		TypeNameResolver.std.setUseFqn(true);
		ModelConverters.getInstance().addConverter(new SubTypedPropertyConverter());
		
	}
}
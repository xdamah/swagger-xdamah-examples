package com.example;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.xdamah.modelconverter.SubTypedPropertyConverter;
import io.swagger.v3.core.converter.ModelConverter;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.core.jackson.ModelResolver;
import io.swagger.v3.core.jackson.TypeNameResolver;
import jakarta.annotation.PostConstruct;

@SpringBootApplication(scanBasePackages = { "io.github.xdamah", "com.example" })
public class PolymosrphismAltFqnExampleApplication {
	public static void main(String[] args) {
		
		SpringApplication.run(PolymosrphismAltFqnExampleApplication.class, args);
	}
	@PostConstruct
	void init()
	{
		TypeNameResolver.std.setUseFqn(true);
		ModelConverters.getInstance().addConverter(new SubTypedPropertyConverter());
		
	}
	
	

}
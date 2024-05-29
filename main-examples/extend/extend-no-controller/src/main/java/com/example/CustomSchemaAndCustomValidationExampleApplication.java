package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.xdamah.modelconverter.ByteArrayPropertyConverter;
import io.github.xdamah.modelresolver.CustomOpenApiValidator;
import io.github.xdamah.util.ModelResolverUtil;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.core.jackson.ModelResolver;
import io.swagger.v3.core.jackson.TypeNameResolver;
import jakarta.annotation.PostConstruct;

@SpringBootApplication(scanBasePackages = { "io.github.xdamah", "com.example" })
public class CustomSchemaAndCustomValidationExampleApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(CustomSchemaAndCustomValidationExampleApplication.class, args);
	}
	

	 @PostConstruct
	 void adjustModelConverters() {
	
		TypeNameResolver.std.setUseFqn(true);
		ModelResolver modelResolver = ModelResolverUtil.originalModelResolver();
		ObjectMapper objectMapper = ModelResolverUtil.objectMapper(modelResolver);
		
		ModelConverters.getInstance().addConverter(new CustomOpenApiValidator(objectMapper));
		ModelConverters.getInstance().addConverter(new ByteArrayPropertyConverter());
		ModelConverters.getInstance().removeConverter(modelResolver);
	}


}
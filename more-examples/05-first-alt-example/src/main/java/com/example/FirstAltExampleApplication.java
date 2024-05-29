package com.example;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.xdamah.modelconverter.ByteArrayPropertyConverter;
import io.github.xdamah.modelresolver.CustomOpenApiValidator;
//import io.github.xdamah.modelconverter.IModelConverters;
import io.swagger.v3.core.converter.ModelConverter;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.core.jackson.ModelResolver;
import jakarta.annotation.PostConstruct;

@SpringBootApplication(scanBasePackages = { "io.github.xdamah", "com.example" })
public class FirstAltExampleApplication {
	private static final Logger logger = LoggerFactory.getLogger(FirstAltExampleApplication.class);
	
	
	

	public static void main(String[] args) {
		SpringApplication.run(FirstAltExampleApplication.class, args);
		
		logger.info("info message on startup");
		logger.debug("debug message on startup");
		logger.warn("warn message on startup");
		logger.error("error message on startup");
	}
	

@PostConstruct

	  void adjustModelConverters() {
	
		List<ModelConverter> converters = ModelConverters.getInstance().getConverters();
		//ModelConverters.getInstance().addConverter(new CustomOpenApiValidator(objectMapper));
		ModelResolver modelResolver=null;
		
		converters = ModelConverters.getInstance().getConverters();
		
		
		for (ModelConverter modelConverter : converters) {
			
			if(modelConverter instanceof ModelResolver)
			{
				modelResolver=(ModelResolver) modelConverter;
			}
			
		}
		ObjectMapper objectMapper=null;
		if(modelResolver!=null)
		{
			objectMapper=modelResolver.objectMapper();
		}
		else
		{
			//
			objectMapper= new ObjectMapper();
		}
		
		ModelConverters.getInstance().addConverter(new CustomOpenApiValidator(objectMapper));
		ModelConverters.getInstance().addConverter(new ByteArrayPropertyConverter());
		ModelConverters.getInstance().removeConverter(modelResolver);
	}

}
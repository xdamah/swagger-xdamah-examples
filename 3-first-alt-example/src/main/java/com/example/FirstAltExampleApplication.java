package com.example;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.xdamah.modelconverter.ByteArrayPropertyConverter;
import io.github.xdamah.modelconverter.IModelConverters;
import io.swagger.v3.core.converter.ModelConverter;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.core.jackson.ModelResolver;
import io.swagger.v3.core.util.Json31;
import jakarta.annotation.PostConstruct;

@SpringBootApplication(scanBasePackages = { "io.github.xdamah", "com.example" })
public class FirstAltExampleApplication {
	private static final Logger logger = LoggerFactory.getLogger(FirstAltExampleApplication.class);
	@Bean
	public IModelConverters modelConverters()
	{
		return new IModelConverters() {
			
			@Override
			public ModelConverter[] converters() {
				
				return new ModelConverter[] {new ByteArrayPropertyConverter()};
			}
		};
	}
	

	public static void main(String[] args) {
		List<ModelConverter> converters = ModelConverters.getInstance().getConverters();
		//ModelConverters.getInstance().addConverter(new CustomOpenApiValidator(objectMapper));
		ModelResolver modelResolver=null;
		System.out.println("converters.size="+converters.size());
		converters = ModelConverters.getInstance().getConverters();
		System.out.println("converters.size="+converters.size());
		for (ModelConverter modelConverter : converters) {
			System.out.println("modelConverter="+modelConverter.getClass().getName());
		}
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
		ModelConverters.getInstance().removeConverter(modelResolver);
		SpringApplication.run(FirstAltExampleApplication.class, args);
		converters = ModelConverters.getInstance().getConverters();
		System.out.println("converters.size="+converters.size());
		for (ModelConverter modelConverter : converters) {
			System.out.println("modelConverter="+modelConverter.getClass().getName());
		}
		logger.info("info message on startup");
		logger.debug("debug message on startup");
		logger.warn("warn message on startup");
		logger.error("error message on startup");
	}

}
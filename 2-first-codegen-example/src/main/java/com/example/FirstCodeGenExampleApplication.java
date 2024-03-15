package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import jakarta.annotation.PostConstruct;

@SpringBootApplication(scanBasePackages = { "io.github.xdamah", "com.example" })
public class FirstCodeGenExampleApplication {
	private static final Logger logger = LoggerFactory.getLogger(FirstCodeGenExampleApplication.class);

	public static void main(String[] args) {

		SpringApplication.run(FirstCodeGenExampleApplication.class, args);
		logger.info("info message on startup");
		logger.debug("debug message on startup");
		logger.warn("warn message on startup");
		logger.error("error message on startup");
	}
	@Autowired
	ObjectMapper mapper;
	@PostConstruct
	void check()
	{
		System.out.println(getConfigDetails(mapper));
	}
	
	public static String getConfigDetails(ObjectMapper mapper) {
		  StringBuilder sb = new StringBuilder();

		  sb.append("Modules:\n");
		  if (mapper.getRegisteredModuleIds().isEmpty()) {
		    sb.append("\t").append("-none-").append("\n");
		  }
		  for (Object m : mapper.getRegisteredModuleIds()) {
		    sb.append("  ").append(m).append("\n");
		  }

		  sb.append("\nSerialization Features:\n");
		  for (SerializationFeature f : SerializationFeature.values()) {
		    sb.append("\t").append(f).append(" -> ").append(mapper.getSerializationConfig().hasSerializationFeatures(f.getMask()));
		    if (f.enabledByDefault()) {
		      sb.append(" (enabled by default)");
		    }
		    sb.append("\n");
		  }

		  sb.append("\nDeserialization Features:\n");
		  for (DeserializationFeature f : DeserializationFeature.values()) {
		    sb.append("\t").append(f).append(" -> ").append(mapper.getDeserializationConfig().hasDeserializationFeatures(f.getMask()));
		    if (f.enabledByDefault()) {
		      sb.append(" (enabled by default)");
		    }
		    sb.append("\n");
		  }

		  return sb.toString();
		}

}
package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;



@SpringBootApplication(scanBasePackages = {"com.github.xdamah", "com.example"})
public class WithBasicSecurityExampleApplication {
	private static final Logger logger = LoggerFactory.getLogger(WithBasicSecurityExampleApplication.class);
	public static void main(String[] args) {
		
		SpringApplication.run(WithBasicSecurityExampleApplication.class, args);
		logger.info("info message on startup");
		logger.debug("debug message on startup");
		logger.warn("warn message on startup");
		logger.error("error message on startup");
	}
	
	@Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }

}
package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@SpringBootApplication(scanBasePackages = { "io.github.xdamah", "com.example" })
public class WithBasicSecurityExampleApplication {
	

	public static void main(String[] args) {

		SpringApplication.run(WithBasicSecurityExampleApplication.class, args);

	}

	@Bean
	MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
		return new MvcRequestMatcher.Builder(introspector);
	}

}
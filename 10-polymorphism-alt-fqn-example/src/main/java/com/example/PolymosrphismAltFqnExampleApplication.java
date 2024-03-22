package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
	}

}
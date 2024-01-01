package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.api.DefApiDelegate;



@SpringBootApplication(scanBasePackages = {"com.github.xdamah", "com.example"})
public class KitchenSinkApplication {
	public static void main(String[] args) {
		
			SpringApplication.run(KitchenSinkApplication.class, args);
	}
	

	@Bean
	DefApiDelegate defApiDelegate()
	{
		return new DefApiDelegateImpl();
	}

	
	

}
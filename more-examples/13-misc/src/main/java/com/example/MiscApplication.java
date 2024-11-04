package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.api.DesignfirstApiDelegate;
import com.example.delegateimpl.DesignfirstApiDelegateImpl;

@SpringBootApplication(scanBasePackages = { "io.github.xdamah", "com.example" })
public class MiscApplication {
	public static void main(String[] args) {

		SpringApplication.run(MiscApplication.class, args);
	}

	@Bean
	DesignfirstApiDelegate defApiDelegate() {
		return new DesignfirstApiDelegateImpl();
	}

}
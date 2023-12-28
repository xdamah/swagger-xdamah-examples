package com.example;

import java.util.Collection;
import java.util.List;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import com.example.api.DefApiDelegate;
import com.example.custom.SampleCustomType;


import com.fasterxml.jackson.databind.module.SimpleModule;

import jakarta.annotation.PostConstruct;



@SpringBootApplication(scanBasePackages = {"com.github.xdamah", "com.example"})
public class FirstExampleApplication {
	public static void main(String[] args) {
		
		

		SpringApplication.run(FirstExampleApplication.class, args);
	}
	

	
	@Bean
	DefApiDelegate defApiDelegate()
	{
		return new DefApiDelegateImpl();
	}

	
	
	/*
	 * no longer needed
	 * @Bean
	  public Jackson2ObjectMapperBuilderCustomizer 
	        jacksonObjectMapperBuilderCustomizer() {
	    return new Jackson2ObjectMapperBuilderCustomizer() {
	    
	      @Override
	      public void customize(
	              Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
	    	  SimpleModule module = new SimpleModule("SampleCustomType");
	  		module.addSerializer(SampleCustomType.class, new SampleCustomTypeSerializer());
	  		module.addDeserializer(SampleCustomType.class, new SampleCustomTypeDeserializer());
	  		jacksonObjectMapperBuilder.modulesToInstall(module);
	       //oneway(jacksonObjectMapperBuilder);
	      }

		private void oneway(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
			jacksonObjectMapperBuilder

			        .serializers(
			                new SampleCustomTypeSerializer())
			            .deserializers(
			                new SampleCustomTypeDeserializer());
		}
	    };
	}*/


}
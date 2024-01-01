package com.example;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.converter.json.*;

import java.util.*;

import com.example.model.Person;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;

@Component
public class Client {
	//@Autowired
	//ObjectMapper objectMapper= extracted();
	private ObjectMapper extracted() {
		ObjectMapper objectMapper2 = new ObjectMapper();
		objectMapper2.setSerializationInclusion(Include.NON_NULL);
		
		return objectMapper2;
	}
	//@PostConstruct
//	void init()
//	{
//		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//	    converter.setObjectMapper(objectMapper);
//		restTemplate=new RestTemplate();
//		restTemplate.getMessageConverters().add(0, converter);
//	}
	@Autowired
	RestTemplate restTemplate;
	
	Person post(String abc, Person in)
	{
		 HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Person> request = new HttpEntity<>(in, headers);
		
		Map<String, String> map = new HashMap<>();
		map.put("abc", abc);
		ResponseEntity<Person> postForEntity = restTemplate.postForEntity("http://localhost:8080/abc/{abc}", request, Person.class, map);
		return postForEntity.getBody();
	}
	
	Person post1(String abc, Person in)
	{
		 HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Person> request = new HttpEntity<>(in, headers);
		Map<String, String> map = new HashMap<>();
		map.put("abc", abc);
		ResponseEntity<Person> postForEntity = restTemplate.postForEntity("http://localhost:8080/abc/{abc}", request, Person.class, map);
		return postForEntity.getBody();
	}

}

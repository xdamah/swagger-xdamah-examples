package com.example;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import com.example.model.Person;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@SpringBootTest()

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

class DateTest {
	
	 @Autowired
	 Client client;
	 
	 @Autowired
		ObjectMapper mapper;
	 
	 File dir= new File("D:/dev/priv/wss/basics/swagger-parsing/swagger-parsing-ws4-java18/swagger-stuff/java-parent/spring-boot-rest-parent/code-gen-parent/mytry3/examples");


	@BeforeAll

	void beforeAll() throws InterruptedException {
		//RestClient r;
	}

	@Test
	void testPost() throws IOException {
		Person person=mapper.readValue(new File(dir, "2.json"), Person.class);
		//String str = FileUtils.readFileToString(new File(dir, "2.json"), Charset.defaultCharset());
		//person.setSampleCustomTypeData(null);
		//person.setSomeTimeData(null);
		String in = mapper.writeValueAsString(person);
		System.out.println(in);
	
		//Map map=mapper.readValue(in, Map.class);
	
		Person post = client.post("abc", person);
		String ret = mapper.writeValueAsString(post);
		System.out.println("ret----");
		System.out.println(ret);
	}
	//@Test
	void testPost1() throws IOException {
		Person person=mapper.readValue(new File(dir, "2.json"), Person.class);
		//String str = FileUtils.readFileToString(new File(dir, "2.json"), Charset.defaultCharset());
		//person.setSampleCustomTypeData(null);
		//person.setSomeTimeData(null);
		String in = mapper.writeValueAsString(person);
		System.out.println(in);
	
	
	
		Person post = client.post1("abc", person);
		String ret = mapper.writeValueAsString(post);
		System.out.println("ret----");
		System.out.println(ret);
	}
	

	
	

}

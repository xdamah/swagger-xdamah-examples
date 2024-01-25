package com.example.test;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.example.model.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class FisrstTest {
	
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	ResourceLoader resourceLoader;


	@Test
	void savePersonJsonWithInvalidCCTest() throws Exception {
		badRequest("saveperson/", "examples/1.json", this::invalidCard, "errors/badcc.json");
	}
	
	@Test
	void savePersonJsonWithInvalidAgeTest() throws Exception {
		badRequest("saveperson/", "examples/1.json", this::invalidAge, "errors/invalidAge.json");
	}
	
	@Test
	void savePersonJsonWithInvalidEmail1Test() throws Exception {
		badRequest("saveperson/", "examples/1.json", this::invalidEmail1, "errors/invalidEmail1.json");
	}
	
	private ObjectNode invalidCard(ObjectNode x){
		x=x.put("creditCardNumber", "44444444444444");
		return x;
	};
	
	private ObjectNode invalidEmail1(ObjectNode x){
		x=x.put("email1", "abcxabc.com");
		return x;
	};
	
	private ObjectNode invalidAge(ObjectNode x){
		x=x.put("age", 17);
		return x;
	};

	private void badRequest( String urlSubPath, String inputPathInCp, UnaryOperator<ObjectNode> s, String pathOfExpectationInCp) throws IOException, JsonMappingException, JsonProcessingException {
		ObjectNode inputAsNode = (ObjectNode) getJsonNode(inputPathInCp);
		inputAsNode=s.apply(inputAsNode);
		String input=inputAsNode.toString();
		HttpHeaders headers=new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = 
			      new HttpEntity<String>(input, headers);
		ResponseEntity<String> response = this.restTemplate.postForEntity("http://localhost:" + port + "/"+urlSubPath, request, String.class);
		HttpStatusCode statusCode = response.getStatusCode();
		System.out.println("statusCode="+statusCode);
		assertNotEquals(statusCode.value(),HttpStatus.OK.value());
		assertEquals(statusCode.value(),HttpStatus.BAD_REQUEST.value());
		String output=response.getBody();
		System.out.println("output="+output);
		ObjectNode outputAsJsonNode = (ObjectNode) jsonStringToJsonNode(output);
		ObjectNode expectedResponseBodyNode = (ObjectNode) getJsonNode(pathOfExpectationInCp);
		assertEquals(expectedResponseBodyNode,outputAsJsonNode);
	}

	private JsonNode getJsonNode(String pathInClassPath) throws IOException, JsonMappingException, JsonProcessingException {
		String input = getJsonAsString(pathInClassPath);
		JsonNode jsonStringToJsonNode = jsonStringToJsonNode(input);
		return jsonStringToJsonNode;
	}
	
	@Test
	void savePersonJsonTest() throws Exception {
		//assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/",
		//		String.class)).contains("Hello, World");
		String input = getJsonAsString("examples/1.json");
		JsonNode inputAsNode = jsonStringToJsonNode(input);
		HttpHeaders headers=new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = 
			      new HttpEntity<String>(input, headers);
		ResponseEntity<String> response = this.restTemplate.postForEntity("http://localhost:" + port + "/saveperson/", request, String.class);
		HttpStatusCode statusCode = response.getStatusCode();
		assertEquals(statusCode.value(),HttpStatus.OK.value());
		String output=response.getBody();
		
		
		String inputSomeTimeData = inputAsNode.get("someTimeData").asText();
		((ObjectNode)inputAsNode).remove("someTimeData");
		JsonNode outputAsNode = jsonStringToJsonNode(output);
		String outputSomeTimeData = outputAsNode.get("someTimeData").asText();
		((ObjectNode)outputAsNode).remove("someTimeData");
		System.out.println("inputt="+inputAsNode);
		System.out.println("output="+outputAsNode);
		OffsetDateTime d1 = OffsetDateTime.parse(inputSomeTimeData);
		OffsetDateTime d2 = OffsetDateTime.parse(outputSomeTimeData);
		assertEquals(inputAsNode, outputAsNode);
		d1=d1.withOffsetSameInstant(ZoneOffset.ofHours(0));
		d2=d2.withOffsetSameInstant(ZoneOffset.ofHours(0));
		assertEquals(d1,d2);
	}
	ObjectMapper om = new ObjectMapper();
	private JsonNode jsonStringToJsonNode(String json) throws JsonMappingException, JsonProcessingException
	{
		return om.readTree(json);
	}
	
	
	
	private String getJsonAsString(String pathInCp) throws IOException 
	{
		Resource resource = resourceLoader.getResource(
			      "classpath:"+pathInCp);
		return resource.getContentAsString(StandardCharsets.UTF_8);
		
		
	}

}

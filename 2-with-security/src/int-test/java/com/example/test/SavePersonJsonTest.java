package com.example.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import com.example.model.Person;
import com.example.test.model.Tuple;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

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
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
/*
 * Testing only security.
 * Trusting all tests in previous example
 */
public class SavePersonJsonTest {
	
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;
	
	
	
	@Autowired
	ResourceLoader resourceLoader;
	
	@PostConstruct
	void init()
	{
		restTemplate.getRestTemplate()
		.setErrorHandler(new DefaultResponseErrorHandler() {
		   
		    public boolean hasError(HttpStatus statusCode) {
		        return false;
		    }
		});
	}
	
	@PreDestroy
	void destroy()
	{
		restTemplate.getRestTemplate()
		.setErrorHandler(null);
	}
	
	@Test
	void savePersonAndGetPicJsonTest() throws Exception {
	
		String input = getJsonAsString("examples/2.json");
		ObjectNode inputAsNode = (ObjectNode) jsonStringToJsonNode(input);
		String inputPic = inputAsNode.get("pic").asText();
		HttpHeaders headers=new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = 
			      new HttpEntity<String>(input, headers);
		ResponseEntity<byte[]> response = this.restTemplate.postForEntity("http://localhost:" + port + "/"+"pic", request, byte[].class);
		HttpStatusCode statusCode = response.getStatusCode();
		assertEquals(HttpStatus.OK.value(), statusCode.value());
		byte[] body = response.getBody();
		String encodedPic = Base64.getEncoder().encodeToString(body);
		System.out.println(inputPic.equals(encodedPic));
		assertEquals(inputPic,encodedPic);
	}
	
	@Test
	void savePersonJson401Test() throws Exception {
	
		saveJsonAndExpect401("secured/saveperson/", "examples/1.json");
		
	}

	@Test
	void savePersonJsonTest() throws Exception {
	
		List<Tuple<OffsetDateTime, OffsetDateTime>> list = saveJson("secured/saveperson/", "examples/1.json", this::f1);
		assertEquals(1, list.size());
	}
	
	private List<Tuple<OffsetDateTime, OffsetDateTime>> f1(ObjectNode inputAsNode,  ObjectNode outputAsNode) 
	{
List<Tuple<OffsetDateTime, OffsetDateTime>> list= new ArrayList<>();
		
		String inputSomeTimeData = inputAsNode.get("someTimeData").asText();
		inputAsNode.remove("someTimeData");
		
		String outputSomeTimeData = outputAsNode.get("someTimeData").asText();
		outputAsNode.remove("someTimeData");
		System.out.println("inputt="+inputAsNode);
		System.out.println("output="+outputAsNode);
		OffsetDateTime d1 = OffsetDateTime.parse(inputSomeTimeData);
		OffsetDateTime d2 = OffsetDateTime.parse(outputSomeTimeData);
		d1=d1.withOffsetSameInstant(ZoneOffset.ofHours(0));
		d2=d2.withOffsetSameInstant(ZoneOffset.ofHours(0));
		list.add(new Tuple<OffsetDateTime, OffsetDateTime>(d1, d2));
		return list;
	}
	
	
	
	
	
	
	
	private List<Tuple<OffsetDateTime, OffsetDateTime>> saveJson(String urlSubPath, String inputPathInCp, BiFunction<ObjectNode, ObjectNode, List<Tuple<OffsetDateTime, OffsetDateTime>>> f) throws IOException, JsonMappingException, JsonProcessingException {
		String input = getJsonAsString(inputPathInCp);
		ObjectNode inputAsNode = (ObjectNode) jsonStringToJsonNode(input);
		HttpHeaders headers=new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    //just sample for the example app
	    headers.setBasicAuth("user", "password");
		HttpEntity<String> request = 
			      new HttpEntity<String>(input, headers);
		
		ResponseEntity<String> response = this.restTemplate.postForEntity("http://localhost:" + port + "/"+urlSubPath, request, String.class);
		
		
		  HttpStatusCode statusCode = response.getStatusCode();
		  System.out.println("statusCode="+statusCode);
		  assertEquals(HttpStatus.OK.value(), statusCode.value()); String
		  output=response.getBody(); ObjectNode outputAsNode = (ObjectNode)
		  jsonStringToJsonNode(output);
		  
		  List<Tuple<OffsetDateTime, OffsetDateTime>> list=f.apply(inputAsNode,
		  outputAsNode);
		  
		  
		  assertEquals(inputAsNode, outputAsNode); for (Tuple<OffsetDateTime,
		  OffsetDateTime> tuple : list) { assertEquals(tuple.getX(),tuple.getY()); }
		  return list;
		 
		
		
	}
	private void saveJsonAndExpect401(String urlSubPath, String inputPathInCp) throws IOException, JsonMappingException, JsonProcessingException {
		String input = getJsonAsString(inputPathInCp);
		
		HttpHeaders headers=new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = 
			      new HttpEntity<String>(input, headers);
		try
		{
		ResponseEntity<String> response = this.restTemplate.postForEntity("http://localhost:" + port + "/"+urlSubPath, request, String.class);
		}
		catch(HttpClientErrorException e)
		{
			HttpStatusCode statusCode = e.getStatusCode();
			assertEquals(401, statusCode.value()); 
			
		}
		
		
		 
		 
		
		
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

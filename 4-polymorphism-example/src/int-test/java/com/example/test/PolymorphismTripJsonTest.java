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

import com.example.test.model.Tuple;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
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
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PolymorphismTripJsonTest {
	
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	ResourceLoader resourceLoader;
	
	

	@Test
	void tripTest() throws Exception {
	
		createTrip("createtrip", "examples/new_trip1.json", 1);
		ObjectNode added = addRequest("addrequest/1", "examples/flightRequest.json");
		JsonNode expected = getJsonNode("ok/trip1WithFlightReq.json");
		assertEquals(expected, added);
		added = addRequest("addrequest/1", "examples/carRequest.json");
		expected = getJsonNode("ok/trip1WithFlightAndCarReq.json");
		
		assertEquals(expected, added);
		added = addRequest("addrequest/1", "examples/hotelRequest.json");
		expected = getJsonNode("ok/trip1WithFlightCarAndHotelReq.json");
		assertEquals(expected, added);
		ObjectNode outputAsJsonNode = getTrip(1);
		expected =getJsonNode("ok/trip1WithFlightCarAndHotelReq.json");
		assertEquals(expected ,outputAsJsonNode);
		
		createTrip("createtrip", "examples/new_trip2.json", 2);
		added = addRequest("addrequest/2", "examples/flightRequest.json");
	
		expected = getJsonNode("ok/trip2WithFlightReq.json");
		assertEquals(expected, added);
		outputAsJsonNode = getTrip(2);
		assertEquals(expected ,outputAsJsonNode);
	}

	private ObjectNode getTrip(int tripId) throws JsonMappingException, JsonProcessingException {
		ResponseEntity<String> response = this.restTemplate.getForEntity("http://localhost:" + port + "/"+"trips/"+tripId, String.class);
		HttpStatusCode statusCode = response.getStatusCode();
		System.out.println("statusCode="+statusCode);
		assertEquals(HttpStatus.OK.value(), statusCode.value());
		
		String output=response.getBody();
		System.out.println("output="+output);
		ObjectNode outputAsJsonNode = (ObjectNode) jsonStringToJsonNode(output);
		return outputAsJsonNode;
	}
	
	private ObjectNode addRequest(String urlSubPath, String inputPathInCp) throws IOException, JsonMappingException, JsonProcessingException {
		String input = getJsonAsString(inputPathInCp);
		ObjectNode inputAsNode = (ObjectNode) jsonStringToJsonNode(input);
		HttpHeaders headers=new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = 
			      new HttpEntity<String>(input, headers);
		ResponseEntity<String> response = this.restTemplate.postForEntity("http://localhost:" + port + "/"+urlSubPath, request, String.class);
		HttpStatusCode statusCode = response.getStatusCode();
		assertEquals(HttpStatus.OK.value(), statusCode.value());
		String output=response.getBody();
		ObjectNode outputAsNode = (ObjectNode) jsonStringToJsonNode(output);
		
		
		
		return outputAsNode;
		
		
		
	}
	
	private void createTrip(String urlSubPath, String inputPathInCp, int expectedTripId) throws IOException, JsonMappingException, JsonProcessingException {
		String input = getJsonAsString(inputPathInCp);
		ObjectNode inputAsNode = (ObjectNode) jsonStringToJsonNode(input);
		HttpHeaders headers=new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = 
			      new HttpEntity<String>(input, headers);
		ResponseEntity<String> response = this.restTemplate.postForEntity("http://localhost:" + port + "/"+urlSubPath, request, String.class);
		HttpStatusCode statusCode = response.getStatusCode();
		assertEquals(HttpStatus.OK.value(), statusCode.value());
		String output=response.getBody();
		ObjectNode outputAsNode = (ObjectNode) jsonStringToJsonNode(output);
		
		inputAsNode.put("tripId", expectedTripId);
		
		assertEquals(inputAsNode, outputAsNode);
		
		
		
	}
	
	
	private JsonNode getJsonNode(String pathInClassPath) throws IOException, JsonMappingException, JsonProcessingException {
		String input = getJsonAsString(pathInClassPath);
		JsonNode jsonStringToJsonNode = jsonStringToJsonNode(input);
		return jsonStringToJsonNode;
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

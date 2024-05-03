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
public class SavePersonJsonTest {
	
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	ResourceLoader resourceLoader;
	
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
	void savePersonJsonTest() throws Exception {
	
		List<Tuple<OffsetDateTime, OffsetDateTime>> list = saveJson("saveperson/", "examples/1.json", this::f1);
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
	
	private List<Tuple<OffsetDateTime, OffsetDateTime>> f2(ObjectNode inputAsNode, ObjectNode outputAsNode) 
	{
		List<Tuple<OffsetDateTime, OffsetDateTime>> list= f1(inputAsNode, outputAsNode);
		List<Tuple<OffsetDateTime, OffsetDateTime>> list1= f1((ObjectNode) inputAsNode.get("anotherPerson"), (ObjectNode) outputAsNode.get("anotherPerson"));
		List<Tuple<OffsetDateTime, OffsetDateTime>> list2= f1(((ObjectNode) ((ArrayNode) inputAsNode.get("children")).get(0)), (ObjectNode) ((ArrayNode) outputAsNode.get("children")).get(0));
		ArrayNode array=(ArrayNode) inputAsNode.get("children");
		list.addAll(list1);
		list.addAll(list2);
		return list;
	}
	
	@Test
	void saveNestedPersonJsonTest() throws Exception {
	
		List<Tuple<OffsetDateTime, OffsetDateTime>> list = saveJson("saveperson/", "examples/2.json", this::f2);
		assertEquals(3, list.size());
	}

	@Test
	void savePersonJsonWithInvalidCCTest() throws Exception {
		badRequest("saveperson/", "examples/1.json", this::invalidCard, "errors/badcc.json");
	}
	
	private ObjectNode invalidCard(ObjectNode x){
		x=x.put("creditCardNumber", "44444444444444");
		return x;
	};
	
	private ObjectNode invalidCardsInNested(ObjectNode x){
		 invalidNested(x, this::invalidCard);
		return x;
		
	}

	private void invalidNested(ObjectNode x, UnaryOperator<ObjectNode> s) {
		s.apply(x);
		s.apply((ObjectNode) x.get("anotherPerson"));
		s.apply((ObjectNode) ((ArrayNode) x.get("children")).get(0));
	};
	
	@Test
	void saveNestedPersonJsonWithInvalidCCTest() throws Exception {
		badRequest("saveperson/", "examples/2.json", this::invalidCardsInNested, "errors/badCCsInNested.json");
	}
	
	private ObjectNode invalidAge(ObjectNode x){
		x=x.put("age", 17);
		return x;
	};
	
	private ObjectNode invalidAgeInNested(ObjectNode x){
		 invalidNested(x, this::invalidAge);
		return x;
		
	}
	
	@Test
	void saveNestedPersonJsonWithInvalidAgeTest() throws Exception {
		badRequest("saveperson/", "examples/2.json", this::invalidAgeInNested, "errors/invalidAgeNested.json");
	}
	
	@Test
	void savePersonJsonWithInvalidAgeTest() throws Exception {
		badRequest("saveperson/", "examples/1.json", this::invalidAge, "errors/invalidAge.json");
	}
	
	private ObjectNode invalidEmail1(ObjectNode x){
		x=x.put("email1", "abcxabc.com");
		return x;
	};
	
	private ObjectNode invalidEmail1InNested(ObjectNode x){
		 invalidNested(x, this::invalidEmail1);
		return x;
		
	}
	@Test
	void savePersonJsonWithInvalidEmail1Test() throws Exception {
		badRequest("saveperson/", "examples/1.json", this::invalidEmail1, "errors/invalidEmail1.json");
	}
	
	@Test
	void saveNestedPersonJsonWithInvalidEmail1Test() throws Exception {
		badRequest("saveperson/", "examples/2.json", this::invalidEmail1InNested, "errors/invalidEmail1Nested.json");
	}
	
	
	@Test
	void savePersonJsonWithInvalidCardAgeEmail1Test() throws Exception {
		badRequest("saveperson/", "examples/1.json", this::invalidCardAgeEmail1, "errors/inalidCCAgeEmail1.json");
	}
	
	@Test
	void saveNestedPersonJsonWithInvalidCardAgeEmail1Test() throws Exception {
		badRequest("saveperson/", "examples/2.json", this::invalidCardAgeEmail1InNested, "errors/invalidCCAgeEmail1Nested.json");
	}
	
	
	
	private ObjectNode invalidCardAgeEmail1(ObjectNode x){
		
		return invalidAge(invalidEmail1(invalidCard(x)));
	};
	
	private ObjectNode invalidCardAgeEmail1InNested(ObjectNode x){
		 invalidNested(x, this::invalidCardAgeEmail1);
		return x;
		
	}
	
	
	@Test
	void getPersonUsingPath() throws Exception {
	
		ResponseEntity<String> response = this.restTemplate.getForEntity("http://localhost:" + port + "/"+"person/byid/1", String.class);
		HttpStatusCode statusCode = response.getStatusCode();
		System.out.println("statusCode="+statusCode);
		assertEquals(HttpStatus.OK.value(), statusCode.value());
		
		String output=response.getBody();
		System.out.println("output="+output);
		ObjectNode outputAsJsonNode = (ObjectNode) jsonStringToJsonNode(output);
		ObjectNode expectedResponseBodyNode = (ObjectNode) getJsonNode("ok/onpath.json");
		assertEquals(expectedResponseBodyNode,outputAsJsonNode);
	}
	
	@Test
	void getPersonUsingMissingPath() throws Exception {
	
		ResponseEntity<String> response = this.restTemplate.getForEntity("http://localhost:" + port + "/"+"person/byid/", String.class);
		HttpStatusCode statusCode = response.getStatusCode();
		System.out.println("statusCode="+statusCode);
		assertEquals(HttpStatus.NOT_FOUND.value(), statusCode.value());
		
		String output=response.getBody();
		System.out.println("output="+output);
//		ObjectNode outputAsJsonNode = (ObjectNode) jsonStringToJsonNode(output);
//		ObjectNode expectedResponseBodyNode = (ObjectNode) getJsonNode("errors/missingPath.json");
//		assertEquals(expectedResponseBodyNode,outputAsJsonNode);
	}
	
	@Test
	void getPersonUsingNonNumericPathPath() throws Exception {
	
		ResponseEntity<String> response = this.restTemplate.getForEntity("http://localhost:" + port + "/"+"person/byid/ab", String.class);
		HttpStatusCode statusCode = response.getStatusCode();
		System.out.println("statusCode="+statusCode);
		assertEquals(HttpStatus.BAD_REQUEST.value(), statusCode.value());
		
		String output=response.getBody();
		System.out.println("output="+output);
		ObjectNode outputAsJsonNode = (ObjectNode) jsonStringToJsonNode(output);
		ObjectNode expectedResponseBodyNode = (ObjectNode) getJsonNode("errors/onNonNumericPath.json");
		assertEquals(expectedResponseBodyNode,outputAsJsonNode);
	}
	
	
	
	

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
		assertNotEquals(HttpStatus.OK.value(), statusCode.value());
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
	
	
	private List<Tuple<OffsetDateTime, OffsetDateTime>> saveJson(String urlSubPath, String inputPathInCp, BiFunction<ObjectNode, ObjectNode, List<Tuple<OffsetDateTime, OffsetDateTime>>> f) throws IOException, JsonMappingException, JsonProcessingException {
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
		
		List<Tuple<OffsetDateTime, OffsetDateTime>> list=f.apply(inputAsNode, outputAsNode);
		
		
		assertEquals(inputAsNode, outputAsNode);
		for (Tuple<OffsetDateTime, OffsetDateTime> tuple : list) {
			assertEquals(tuple.getX(),tuple.getY());
		}
		return list;
		
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

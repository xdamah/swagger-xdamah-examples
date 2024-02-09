package com.example.test;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Disabled;
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
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.LongNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SavePersonJsonTest {
	
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	ResourceLoader resourceLoader;
	
	

	@Test
	void savePersonJsonTest() throws Exception {
	
		List<Tuple<OffsetDateTime, OffsetDateTime>> list = saveJson("person/id1?def=18&defArr=1&defArr=2&defArr=3&x=2024-01-12", "examples/1.json", this::f1);
		assertEquals(1, list.size());
	}
	
	@Test
	void saveNestedPersonJsonTest() throws Exception {
	
		List<Tuple<OffsetDateTime, OffsetDateTime>> list = saveJson("person/id1?def=18&defArr=1&defArr=2&defArr=3&x=2024-01-12", "examples/2.json", this::f2);
		assertEquals(3, list.size());
	}
	
	@Test
	void savePersonaJsonTest() throws Exception {
	
		List<Tuple<OffsetDateTime, OffsetDateTime>> list = saveJson("persona/id1?def=18&defArr=1&defArr=2&defArr=3&x=2024-01-12", "examples/1.json", this::f1);
		assertEquals(1, list.size());
	}
	
	@Test
	void saveNestedPersonaJsonTest() throws Exception {
	
		List<Tuple<OffsetDateTime, OffsetDateTime>> list = saveJson("persona/id1?def=18&defArr=1&defArr=2&defArr=3&x=2024-01-12", "examples/2.json", this::f2);
		assertEquals(3, list.size());
	}
	
	@Test
	void savePersonbJsonTest() throws Exception {
	
		List<Tuple<OffsetDateTime, OffsetDateTime>> list = saveJson("personb/id1?def=18&defArr=1&defArr=2&defArr=3&x=2024-01-12", "examples/1.json", this::f1);
		assertEquals(1, list.size());
	}
	
	@Test
	void saveNestedPersonbJsonTest() throws Exception {
	
		List<Tuple<OffsetDateTime, OffsetDateTime>> list = saveJson("personb/id1?def=18&defArr=1&defArr=2&defArr=3&x=2024-01-12", "examples/2.json", this::f2);
		assertEquals(3, list.size());
	}
	
	
	
	
	//for above work on bad parameters also
	//for below work on 1.json also
	
	@Test
	void savePersonAndGetPicJsonTest() throws Exception {
	
		savePersonAndGetPicInternal("pic", null);
	}
	
	@Test
	void savePersonAndGetPic1JsonAcceptJpegTest() throws Exception {
	
		savePersonAndGetPicInternal("pic1", MediaType.IMAGE_JPEG);
	}
	@Test
	void savePersonAndGetPic1JsonAcceptPngTest() throws Exception {
	
		savePersonAndGetPicInternal("pic1", MediaType.IMAGE_PNG);
	}
	
	@Test
	@Disabled
	//TODO do better test to recotrd failure on gif
	void savePersonAndGetPic1JsonAcceptGifTest() throws Exception {
	
		savePersonAndGetPicInternal("pic1", MediaType.IMAGE_GIF);
	}
	
	@Test
	void getPersonUsingQueryById() throws Exception {
	
		getPersonInternal("person/byid?id=1", "ok/onquerybyid.json");
	}

	
	@Test
	void getPersonUsingQueryByIds() throws Exception {
		getPersonInternal("person/byids?ids=1&ids=2&ids=3", "ok/onquerybyids.json");
	}
	@Test
	void savePersonStringBodyJsonTest() throws Exception {
	
		List<Tuple<OffsetDateTime, OffsetDateTime>> list = saveJson("stringreqbody/id1?def=18&defArr=1&defArr=2&defArr=3&x=2024-01-12", "examples/1.json", this::f1);
		assertEquals(1, list.size());
	}
	
	@Test
	void saveNestedPersonStringBodyJsonTest() throws Exception {
	
		List<Tuple<OffsetDateTime, OffsetDateTime>> list = saveJson("stringreqbody/id1?def=18&defArr=1&defArr=2&defArr=3&x=2024-01-12", "examples/2.json", this::f2);
		assertEquals(3, list.size());
	}
	
	@Test
	void binaryTest() throws Exception {
	
		ObjectNode jsonNode = (ObjectNode) getJsonNode("examples/2.json");
		TextNode picNode = (TextNode) jsonNode.get("pic");
		String picInBase64 = picNode.asText();
		byte[] picBytes = Base64.getDecoder().decode(picInBase64);
		URL url = new URL("http://localhost:"+port+"/binary");
		URLConnection con = url.openConnection();
		HttpURLConnection http = (HttpURLConnection)con;
		http.setRequestMethod("POST"); // PUT is another valid option
		http.setDoOutput(true);

		http.setFixedLengthStreamingMode(picBytes.length);
		http.setRequestProperty("Content-Type", MediaType.APPLICATION_OCTET_STREAM_VALUE);
		http.setRequestProperty("Accept", MediaType.IMAGE_JPEG_VALUE);
		
		
		http.connect();
				try(OutputStream os = http.getOutputStream()) {
		    os.write(picBytes);
		}
		int httpStatusCode = http.getResponseCode();
				
		assertEquals(200, httpStatusCode);	
		

		try(InputStream is = httpStatusCode==400?http.getErrorStream():http.getInputStream();)
		{
			byte[] imageReturned = IOUtils.toByteArray(is);
			if(httpStatusCode==400)
			{
				System.out.println(new String(imageReturned));
			}
			
			assertEquals(picBytes.length, imageReturned.length);
			assertTrue(Arrays.equals(imageReturned, imageReturned));
		}
		
		
	}
	
	@Test
	void anotherControllerGetUsingPathTest() throws Exception {
	
		getPersonInternal("abc/abc", "ok/ongetbypath.json");
	}
	
	@Test
	void anotherControllerPostUsingPathTest() throws Exception {
	
		List<Tuple<OffsetDateTime, OffsetDateTime>> list = saveSimplerJson("abc/abc", "examples/1.json", this::f1, "firstName", "abc");
		assertEquals(1, list.size());
	}
	
	@Test
	void anotherControllerNestedPostUsingPathTest() throws Exception {
	
		List<Tuple<OffsetDateTime, OffsetDateTime>> list = saveSimplerJson("abc/abc", "examples/2.json", this::f2, "firstName", "abc");
		assertEquals(3, list.size());
	}
	
	@Test
	void defControllerPostUsingPathTest() throws Exception {
		//for now lets not worry about why not 19l
		//thats because when loading from json its becoming that an int and to ensure it compares well passing it an int
		List<Tuple<OffsetDateTime, OffsetDateTime>> list = saveSimplerJson("def?abc=19", "examples/1.json", this::f1, "id", 19);
		assertEquals(1, list.size());
	}
	
	@Test
	void defControllerNestedPostUsingPathTest() throws Exception {
		//for now lets not worry about why not 19l
		//thats because when loading from json its becoming that an int and to ensure it compares well passing it an int		
		List<Tuple<OffsetDateTime, OffsetDateTime>> list = saveSimplerJson("def?abc=19", "examples/2.json", this::f2, "id", 19);
		assertEquals(3, list.size());
	}
	
	@Test
	void savePersonJsonWithInvalidCCAndOtherInvalidParamTest() throws Exception {
		badRequest("person/i?def=17&defArr=1&defArr=2&defArr=3&x=2024-01-12", "examples/1.json", this::invalidCard, "errors/badccAndOtherParams.json");
	}
	
	@Test
	void saveNestedPersonJsonWithInvalidCCAndOtherInvalidParamTest() throws Exception {
		badRequest("person/i?def=17&defArr=1&defArr=2&defArr=3&x=2024-01-12", "examples/2.json", this::invalidCardsInNested, "errors/badCCsInNestedAndOtherParams.json");
	}
	
	@Test
	void savePersonaJsonWithInvalidCCAndOtherInvalidParamTest() throws Exception {
		badRequest("persona/i?def=17&defArr=1&defArr=2&defArr=3&x=2024-01-12", "examples/1.json", this::invalidCard, "errors/badccAndOtherParams.json");
	}
	
	@Test
	void saveNestedPersonaJsonWithInvalidCCAndOtherInvalidParamTest() throws Exception {
		badRequest("persona/i?def=17&defArr=1&defArr=2&defArr=3&x=2024-01-12", "examples/2.json", this::invalidCardsInNested, "errors/badCCsInNestedAndOtherParams.json");
	}
	
	@Test
	void savePersonbJsonWithInvalidCCAndOtherInvalidParamTest() throws Exception {
		badRequest("personb/i?def=17&defArr=1&defArr=2&defArr=3&x=2024-01-12", "examples/1.json", this::invalidCard, "errors/badccAndOtherParams.json");
	}
	
	@Test
	void saveNestedPersonbJsonWithInvalidCCAndOtherInvalidParamTest() throws Exception {
		badRequest("personb/i?def=17&defArr=1&defArr=2&defArr=3&x=2024-01-12", "examples/2.json", this::invalidCardsInNested, "errors/badCCsInNestedAndOtherParams.json");
	}
	
	@Test
	void getPersonUsingMissingQueryById() throws Exception {
	
		ResponseEntity<String> response = this.restTemplate.getForEntity("http://localhost:" + port + "/"+"person/byid", String.class);
		HttpStatusCode statusCode = response.getStatusCode();
		System.out.println("statusCode="+statusCode);
		assertEquals(HttpStatus.BAD_REQUEST.value(), statusCode.value());
		
		String output=response.getBody();
		System.out.println("output="+output);
		ObjectNode outputAsJsonNode = (ObjectNode) jsonStringToJsonNode(output);
		ObjectNode expectedResponseBodyNode = (ObjectNode) getJsonNode("errors/missingQuery.json");
		assertEquals(expectedResponseBodyNode,outputAsJsonNode);
	}
	
	@Test
	void getPersonUsingMissingQueryByIds() throws Exception {
	
		ResponseEntity<String> response = this.restTemplate.getForEntity("http://localhost:" + port + "/"+"person/byids", String.class);
		HttpStatusCode statusCode = response.getStatusCode();
		System.out.println("statusCode="+statusCode);
		assertEquals(HttpStatus.BAD_REQUEST.value(), statusCode.value());
		
		String output=response.getBody();
		System.out.println("output="+output);
		ObjectNode outputAsJsonNode = (ObjectNode) jsonStringToJsonNode(output);
		ObjectNode expectedResponseBodyNode = (ObjectNode) getJsonNode("errors/missingQueryIds.json");
		assertEquals(expectedResponseBodyNode,outputAsJsonNode);
	}

	/*
	 * WIP
	
	
	
	
	
	
	
	
	
	
	@Test
	void saveNestedPersonJsonWithInvalidAgeTest() throws Exception {
		badRequest("saveperson/", "examples/2.json", this::invalidAgeInNested, "errors/invalidAgeNested.json");
	}
	
	@Test
	void savePersonJsonWithInvalidAgeTest() throws Exception {
		badRequest("saveperson/", "examples/1.json", this::invalidAge, "errors/invalidAge.json");
	}
	
	@Test
	void saveNestedPersonJsonWithInvalidEmail1Test() throws Exception {
		badRequest("saveperson/", "examples/2.json", this::invalidEmail1InNested, "errors/invalidEmail1Nested.json");
	}
	
	
	
	@Test
	void savePersonJsonWithInvalidEmail1Test() throws Exception {
		badRequest("saveperson/", "examples/1.json", this::invalidEmail1, "errors/invalidEmail1.json");
	}
	
	
	
	
	
	@Test
	void savePersonJsonWithInvalidCardAgeEmail1Test() throws Exception {
		badRequest("saveperson/", "examples/1.json", this::invalidCardAgeEmail1, "errors/inalidCCAgeEmail1.json");
	}
	
	
	@Test
	void saveNestedPersonJsonWithInvalidCardAgeEmail1Test() throws Exception {
		badRequest("saveperson/", "examples/2.json", this::invalidCardAgeEmail1InNested, "errors/invalidCCAgeEmail1Nested.json");
	}
	

	
	@Test
	void getPersonUsingMissingPath() throws Exception {
	
		ResponseEntity<String> response = this.restTemplate.getForEntity("http://localhost:" + port + "/"+"person/byid/", String.class);
		HttpStatusCode statusCode = response.getStatusCode();
		System.out.println("statusCode="+statusCode);
		assertEquals(HttpStatus.NOT_FOUND.value(), statusCode.value());
		
		String output=response.getBody();
		System.out.println("output="+output);
		ObjectNode outputAsJsonNode = (ObjectNode) jsonStringToJsonNode(output);
		ObjectNode expectedResponseBodyNode = (ObjectNode) getJsonNode("errors/missingPath.json");
		assertEquals(expectedResponseBodyNode,outputAsJsonNode);
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
	*/

	private void getPersonInternal(String subPath, String expectedResponsePath) throws JsonMappingException, JsonProcessingException, IOException {
		ResponseEntity<String> response = this.restTemplate.getForEntity("http://localhost:" + port + "/"+subPath, String.class);
		HttpStatusCode statusCode = response.getStatusCode();
		System.out.println("statusCode="+statusCode);
		assertEquals(HttpStatus.OK.value(), statusCode.value());
		
		String output=response.getBody();
		System.out.println("output="+output);
		ObjectNode outputAsJsonNode = (ObjectNode) jsonStringToJsonNode(output);
		ObjectNode expectedResponseBodyNode = (ObjectNode) getJsonNode(expectedResponsePath);
		assertEquals(expectedResponseBodyNode,outputAsJsonNode);
	}
	
	private void savePersonAndGetPicInternal(String urlPath, MediaType acceptedType) throws IOException, JsonMappingException, JsonProcessingException {
		String input = getJsonAsString("examples/2.json");
		ObjectNode inputAsNode = (ObjectNode) jsonStringToJsonNode(input);
		String inputPic = inputAsNode.get("pic").asText();
		HttpHeaders headers=new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		if(acceptedType!=null)
		{
			List<MediaType> accepts= new ArrayList<>();
			accepts.add(acceptedType);
			headers.setAccept(accepts);
		}
		
		HttpEntity<String> request = 
			      new HttpEntity<String>(input, headers);
		ResponseEntity<byte[]> response = this.restTemplate.postForEntity("http://localhost:" + port + "/"+urlPath, request, byte[].class);
		HttpStatusCode statusCode = response.getStatusCode();
		assertEquals(HttpStatus.OK.value(), statusCode.value());
		byte[] body = response.getBody();
		String encodedPic = Base64.getEncoder().encodeToString(body);
		System.out.println(inputPic.equals(encodedPic));
		assertEquals(inputPic,encodedPic);
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
	
	
	
	private ObjectNode invalidAge(ObjectNode x){
		x=x.put("age", 17);
		return x;
	};
	
	private ObjectNode invalidAgeInNested(ObjectNode x){
		 invalidNested(x, this::invalidAge);
		return x;
		
	}
	
	
	
	private ObjectNode invalidEmail1(ObjectNode x){
		x=x.put("email1", "abcxabc.com");
		return x;
	};
	
	private ObjectNode invalidEmail1InNested(ObjectNode x){
		 invalidNested(x, this::invalidEmail1);
		return x;
		
	}
	
	
	private ObjectNode invalidCardAgeEmail1InNested(ObjectNode x){
		 invalidNested(x, this::invalidCardAgeEmail1);
		return x;
		
	}
	
	private ObjectNode invalidCardAgeEmail1(ObjectNode x){
		
		return invalidAge(invalidEmail1(invalidCard(x)));
	};
	
	
	
	
	

	private void badRequest( String urlSubPath, String inputPathInCp, UnaryOperator<ObjectNode> s, String pathOfExpectationInCp) throws IOException, JsonMappingException, JsonProcessingException {
		int index=urlSubPath.indexOf('?');
		String use=null;
		String use1=null;
		if(index!=-1)
		{
			String before=urlSubPath.substring(0, index);
			int slashIndex = before.indexOf('/');
			if(slashIndex!=-1)
			{
				use1=before.substring(0, slashIndex);
				use1='/'+use1+"/{id}";
			}
			use='/'+before;
		}
		
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
		String expectedResponseBodyJson = getJsonAsString(pathOfExpectationInCp);
		expectedResponseBodyJson=expectedResponseBodyJson.replaceAll(Pattern.quote("/person/i"), use);
		expectedResponseBodyJson=expectedResponseBodyJson.replaceAll(Pattern.quote("/person/{id}"), use1);
		System.out.println("modified="+expectedResponseBodyJson);
		ObjectNode expectedResponseBodyNode = (ObjectNode) jsonStringToJsonNode(expectedResponseBodyJson);
		
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
	    headers.set("header1", "header1val1");
	    
	    headers.set("header2", "h1,h2,h3");//corersponds to style:simple:explode:false 
	    headers.add("Cookie", "cookieValue1=cookieeg1");
	    
		HttpEntity<String> request = 
			      new HttpEntity<String>(input, headers);
		ResponseEntity<String> response = this.restTemplate.postForEntity("http://localhost:" + port + "/"+urlSubPath, request, String.class);
		HttpStatusCode statusCode = response.getStatusCode();
		assertEquals(HttpStatus.OK.value(), statusCode.value());
		String output=response.getBody();
		System.out.println(output);
		ObjectNode outputAsNode = (ObjectNode) jsonStringToJsonNode(output);
		
		List<Tuple<OffsetDateTime, OffsetDateTime>> list=f.apply(inputAsNode, outputAsNode);
		inputAsNode.put("registrationDate", "2024-01-12");//from x
		
		assertEquals(inputAsNode, outputAsNode);
		for (Tuple<OffsetDateTime, OffsetDateTime> tuple : list) {
			assertEquals(tuple.getX(),tuple.getY());
		}
		return list;
		
	}
	
	private List<Tuple<OffsetDateTime, OffsetDateTime>> saveSimplerJson(String urlSubPath, String inputPathInCp, BiFunction<ObjectNode, ObjectNode, List<Tuple<OffsetDateTime, OffsetDateTime>>> f, String fieldName, Object fieldValue) throws IOException, JsonMappingException, JsonProcessingException {
		String input = getJsonAsString(inputPathInCp);
		ObjectNode inputAsNode = (ObjectNode) jsonStringToJsonNode(input);
		//idToLong(inputAsNode);
		HttpHeaders headers=new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	   
	    
		HttpEntity<String> request = 
			      new HttpEntity<String>(input, headers);
		ResponseEntity<String> response = this.restTemplate.postForEntity("http://localhost:" + port + "/"+urlSubPath, request, String.class);
		HttpStatusCode statusCode = response.getStatusCode();
		assertEquals(HttpStatus.OK.value(), statusCode.value());
		String output=response.getBody();
		System.out.println(output);
		ObjectNode outputAsNode = (ObjectNode) jsonStringToJsonNode(output);
		//idToLong(inputAsNode);
		System.out.println("outputAsNode="+outputAsNode);
		
		List<Tuple<OffsetDateTime, OffsetDateTime>> list=f.apply(inputAsNode, outputAsNode);
		if(fieldValue!=null)
		{
			if(fieldValue instanceof String)
			{
				
				String act=(String) fieldValue;
			
				inputAsNode.put(fieldName, act);//from path can avoid hardcode later if neeeded
			}
			else if(fieldValue instanceof Long)
			{
				
				Long act=(Long) fieldValue;
				
				inputAsNode.put(fieldName, act);//from path can avoid hardcode later if neeeded
			}
			else if(fieldValue instanceof Integer)
			{
				
				Integer act=(Integer) fieldValue;
				
				inputAsNode.put(fieldName, act);//from path can avoid hardcode later if neeeded
			}
			else
			{
				throw new RuntimeException("yet to handle "+fieldValue.getClass().getName());
			}	
		}
		
		
		assertEquals(inputAsNode, outputAsNode);
		
		for (Tuple<OffsetDateTime, OffsetDateTime> tuple : list) {
			assertEquals(tuple.getX(),tuple.getY());
		}
		return list;
		
	}

	private void idToLong(ObjectNode inputAsNode) {
		JsonNode jsonNode = inputAsNode.get("id");
		if(jsonNode!=null)
		{
			if(jsonNode instanceof LongNode)
			{
				//do nothing
			}
			else if(jsonNode instanceof IntNode)
			{
				IntNode idNode = (IntNode) inputAsNode.get("id");
				if(idNode!=null)
				{
					int idasInt = idNode.asInt();
					inputAsNode.put("id", Long.valueOf(idasInt));
				}
			}
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

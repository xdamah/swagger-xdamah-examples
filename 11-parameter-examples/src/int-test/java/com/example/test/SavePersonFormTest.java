package com.example.test;

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
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.example.model.Person;
import com.example.test.model.BridgePerson;
import com.example.test.model.Tuple;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SavePersonFormTest {
	
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	ResourceLoader resourceLoader;
	
	@Test
	void savePersonFormTest() throws Exception {
	
		List<Tuple<OffsetDateTime, OffsetDateTime>> list = saveForm("person/id1?def=18&defArr=1&defArr=2&defArr=3&x=2024-01-12", "examples/1.form.properties", this::f1);
		assertEquals(1, list.size());
	}
	
	@Test
	void saveNestedPersonFormTest() throws Exception {
	
		List<Tuple<OffsetDateTime, OffsetDateTime>> list = saveForm("person/id1?def=18&defArr=1&defArr=2&defArr=3&x=2024-01-12", "examples/2.form.properties", this::f2);
		assertEquals(3, list.size());
	}
	
	@Test
	void savePersonaFormTest() throws Exception {
	
		List<Tuple<OffsetDateTime, OffsetDateTime>> list = saveForm("persona/id1?def=18&defArr=1&defArr=2&defArr=3&x=2024-01-12", "examples/1.form.properties", this::f1);
		assertEquals(1, list.size());
	}
	
	@Test
	void saveNestedPersonaFormTest() throws Exception {
	
		List<Tuple<OffsetDateTime, OffsetDateTime>> list = saveForm("persona/id1?def=18&defArr=1&defArr=2&defArr=3&x=2024-01-12", "examples/2.form.properties", this::f2);
		assertEquals(3, list.size());
	}
	
	@Test
	void savePersonbFormTest() throws Exception {
	
		List<Tuple<OffsetDateTime, OffsetDateTime>> list = saveForm("personb/id1?def=18&defArr=1&defArr=2&defArr=3&x=2024-01-12", "examples/1.form.properties", this::f1);
		assertEquals(1, list.size());
	}
	
	@Test
	void saveNestedPersonbFormTest() throws Exception {
	
		List<Tuple<OffsetDateTime, OffsetDateTime>> list = saveForm("personb/id1?def=18&defArr=1&defArr=2&defArr=3&x=2024-01-12", "examples/2.form.properties", this::f2);
		assertEquals(3, list.size());
	}
	//for above work on bad parameters also
	
	
	@Test
	void savePersonFormWithInvalidAgeAndOtherInvalidParamTest() throws Exception {
		badRequest("person/i?def=17&defArr=1&defArr=2&defArr=3&x=2024-01-12", "examples/1.form.properties", this::invalidAge, "errors/invalidAgeAndOtherParamsForm.json");
	}
	
	@Test
	
	void saveNestedPersonFormWithInvalidAgeAndOtherInvalidParamTest() throws Exception {
		badRequest("person/i?def=17&defArr=1&defArr=2&defArr=3&x=2024-01-12", "examples/2.form.properties", this::invalidAgeInNested, "errors/invalidAgeNestedAndOtherParamsFrom.json");
	}
	
	@Test
	void savePersonaFormWithInvalidAgeAndOtherInvalidParamTest() throws Exception {
		badRequest("persona/i?def=17&defArr=1&defArr=2&defArr=3&x=2024-01-12", "examples/1.form.properties", this::invalidAge, "errors/invalidAgeAndOtherParamsForm.json");
	}
	
	@Test

	void saveNestedPersonaFormWithInvalidAgeAndOtherInvalidParamTest() throws Exception {
		badRequest("persona/i?def=17&defArr=1&defArr=2&defArr=3&x=2024-01-12", "examples/2.form.properties", this::invalidAgeInNested, "errors/invalidAgeNestedAndOtherParamsFrom.json");
	}
	
	
	@Test
	
	void savePersonFomWithInvalidCCAndOtherInvalidParamTest() throws Exception {
		badRequest("person/i?def=17&defArr=1&defArr=2&defArr=3&x=2024-01-12", "examples/1.form.properties", this::invalidCard, "errors/badccAndOtherParams.json");
	}


	
	
	

	private void savePersonAndGetPicInternal(String urlSubPath, MediaType acceptedType) throws IOException {
		Properties  props = getFormJsonAsProperties("examples/2.form.properties");
		
		String inputPic = props.getProperty("pic");
		Set<Object> keySet = props.keySet();
		
		HttpHeaders headers=new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	    if(acceptedType!=null)
		{
			List<MediaType> accepts= new ArrayList<>();
			accepts.add(acceptedType);
			headers.setAccept(accepts);
		}
	    MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
	    for (Object keyObject : keySet) {
			String key=(String) keyObject;
			String val=props.getProperty(key);
			map.add(key, val);
		}
	    

	    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

		ResponseEntity<byte[]> response = this.restTemplate.postForEntity("http://localhost:" + port + "/"+urlSubPath, request, byte[].class);
		HttpStatusCode statusCode = response.getStatusCode();
		assertEquals(HttpStatus.OK.value(), statusCode.value());
		byte[] body = response.getBody();
		String encodedPic = Base64.getEncoder().encodeToString(body);

		assertEquals(inputPic,encodedPic);
	}
	
	
		

	

	
	private List<Tuple<OffsetDateTime, OffsetDateTime>> f1(Properties props,   ObjectNode outputAsNode) 
	{
List<Tuple<OffsetDateTime, OffsetDateTime>> list= new ArrayList<>();
		
addToListTuple(props, outputAsNode, list, "someTimeData");
		return list;
	}
	
	private List<Tuple<OffsetDateTime, OffsetDateTime>> f2(Properties inputAsNode, ObjectNode outputAsNode) 
	{
		
		List<Tuple<OffsetDateTime, OffsetDateTime>> list= f1(inputAsNode, outputAsNode);
		
		addToListTuple(inputAsNode, (ObjectNode) outputAsNode.get("anotherPerson"), list, "anotherPerson.someTimeData");

		addToListTuple(inputAsNode, (ObjectNode) ((ArrayNode) outputAsNode.get("children")).get(0), list, "children[0].someTimeData");

		return list;
	}

	private void addToListTuple(Properties props, ObjectNode objectNode,
			List<Tuple<OffsetDateTime, OffsetDateTime>> list, String propKey) {
		String inputSomeTimeData = props.getProperty(propKey);
		props.remove(propKey);
		
		String outputSomeTimeData = objectNode.get("someTimeData").asText();
		objectNode.remove("someTimeData");
		OffsetDateTime d1 = OffsetDateTime.parse(inputSomeTimeData);
		OffsetDateTime d2 = OffsetDateTime.parse(outputSomeTimeData);
		d1=d1.withOffsetSameInstant(ZoneOffset.ofHours(0));
		d2=d2.withOffsetSameInstant(ZoneOffset.ofHours(0));
		list.add(new Tuple<OffsetDateTime, OffsetDateTime>(d1, d2));
	}
	
	private List<Tuple<OffsetDateTime, OffsetDateTime>> saveForm(String urlSubPath, String inputPathInCp, 
			BiFunction<Properties,  ObjectNode, List<Tuple<OffsetDateTime, OffsetDateTime>>> f) throws IOException, JsonMappingException, JsonProcessingException {
		Properties  props = getFormJsonAsProperties(inputPathInCp);
		props.setProperty("registrationDate", "2024-01-12");//from x
		Set<Object> keySet = props.keySet();
		
		HttpHeaders headers=new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	    headers.set("header1", "header1val1");
	    
	    headers.set("header2", "h1,h2,h3");//corersponds to style:simple:explode:false 
	    headers.add("Cookie", "cookieValue1=cookieeg1");
	    MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
	    for (Object keyObject : keySet) {
			String key=(String) keyObject;
			String val=props.getProperty(key);
			map.add(key, val);
		}
	    

	    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

		
		ResponseEntity<String> response = this.restTemplate.postForEntity("http://localhost:" + port + "/"+urlSubPath, request, String.class);
		HttpStatusCode statusCode = response.getStatusCode();
		assertEquals(HttpStatus.OK.value(), statusCode.value());
		String output=response.getBody();
		System.out.println("output="+output);
		ObjectNode outputAsNode = (ObjectNode) jsonStringToJsonNode(output);
		
		List<Tuple<OffsetDateTime, OffsetDateTime>> list=f.apply(props,  outputAsNode);
		
		
		Field[] declaredFields = Person.class.getDeclaredFields();
		
		Set<String> fieldNames=new LinkedHashSet<>();
		for (Field field : declaredFields) {
			fieldNames.add(field.getName());
		}

		fieldNames.remove("anotherPerson");
		fieldNames.remove("children");
		//even someTimeData can be removed but no need to do that
		iterateOneLevel("", props, outputAsNode, fieldNames);
		iterateOneLevel("anotherPerson.", props, (ObjectNode) outputAsNode.get("anotherPerson"), fieldNames);
		//(ObjectNode) ((ArrayNode) outputAsNode.get("children"))
		ArrayNode children= (ArrayNode) outputAsNode.get("children");
		ObjectNode target=children!=null?(ObjectNode) children.get(0):null;
		iterateOneLevel("children[0].", props, target, fieldNames);
		//assertEquals(inputAsNode, outputAsNode);//instead we are using the iterate methods above
		for (Tuple<OffsetDateTime, OffsetDateTime> tuple : list) {
			assertEquals(tuple.getX(),tuple.getY());
		}
		return list;
		
	}
	
	
	
	
	
	//problem in this code the header is going with "Content-Type 'application/x-www-form-urlencoded;charset=UTF-8'

	//how to remove that UTF-8 or s
	private List<Tuple<OffsetDateTime, OffsetDateTime>> saveSimplerForm(String urlSubPath, String inputPathInCp, 
			BiFunction<Properties,  ObjectNode, List<Tuple<OffsetDateTime, OffsetDateTime>>> f) throws IOException, JsonMappingException, JsonProcessingException {
		Properties  props = getFormJsonAsProperties(inputPathInCp);
		props.setProperty("firstName", "abc");//from path avoid hardcoding later if needed.
		Set<Object> keySet = props.keySet();
		
		HttpHeaders headers=new HttpHeaders();
	    //headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.set("Content-Type", "application/x-www-form-urlencoded");
	   
	    MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
	    for (Object keyObject : keySet) {
			String key=(String) keyObject;
			String val=props.getProperty(key);
			map.add(key, val);
		}
	    

	    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
	    ResponseEntity<String> response = this.restTemplate.postForEntity("http://localhost:" + port + "/"+urlSubPath, request, String.class);
	
		HttpStatusCode statusCode = response.getStatusCode();
		//assertEquals(HttpStatus.OK.value(), statusCode.value());
		String output=response.getBody();
		System.out.println("output="+output);
		ObjectNode outputAsNode = (ObjectNode) jsonStringToJsonNode(output);
		
		List<Tuple<OffsetDateTime, OffsetDateTime>> list=f.apply(props,  outputAsNode);
		
		
		Field[] declaredFields = Person.class.getDeclaredFields();
		
		Set<String> fieldNames=new LinkedHashSet<>();
		for (Field field : declaredFields) {
			fieldNames.add(field.getName());
		}

		fieldNames.remove("anotherPerson");
		fieldNames.remove("children");
		//even someTimeData can be removed but no need to do that
		iterateOneLevel("", props, outputAsNode, fieldNames);
		iterateOneLevel("anotherPerson.", props, (ObjectNode) outputAsNode.get("anotherPerson"), fieldNames);
		//(ObjectNode) ((ArrayNode) outputAsNode.get("children"))
		ArrayNode children= (ArrayNode) outputAsNode.get("children");
		ObjectNode target=children!=null?(ObjectNode) children.get(0):null;
		iterateOneLevel("children[0].", props, target, fieldNames);
		//assertEquals(inputAsNode, outputAsNode);//instead we are using the iterate methods above
		for (Tuple<OffsetDateTime, OffsetDateTime> tuple : list) {
			assertEquals(tuple.getX(),tuple.getY());
		}
		return list;
		
	}
	
	private List<Tuple<OffsetDateTime, OffsetDateTime>> saveSimplerForm1(String urlSubPath, String inputPathInCp, 
			BiFunction<Properties,  ObjectNode, List<Tuple<OffsetDateTime, OffsetDateTime>>> f) throws IOException, JsonMappingException, JsonProcessingException {
		Properties  props = getFormJsonAsProperties(inputPathInCp);
		props.setProperty("id", "19");//from path avoid hardcoding later if needed.
		Set<Object> keySet = props.keySet();
		
		HttpHeaders headers=new HttpHeaders();
	    //headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.set("Content-Type", "application/x-www-form-urlencoded");
	   
	    MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
	    for (Object keyObject : keySet) {
			String key=(String) keyObject;
			String val=props.getProperty(key);
			map.add(key, val);
		}
	    

	    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
	    ResponseEntity<String> response = this.restTemplate.postForEntity("http://localhost:" + port + "/"+urlSubPath, request, String.class);
	
		HttpStatusCode statusCode = response.getStatusCode();
		assertEquals(HttpStatus.OK.value(), statusCode.value());
		String output=response.getBody();
		System.out.println("output="+output);
		ObjectNode outputAsNode = (ObjectNode) jsonStringToJsonNode(output);
		
		List<Tuple<OffsetDateTime, OffsetDateTime>> list=f.apply(props,  outputAsNode);
		
		
		Field[] declaredFields = Person.class.getDeclaredFields();
		
		Set<String> fieldNames=new LinkedHashSet<>();
		for (Field field : declaredFields) {
			fieldNames.add(field.getName());
		}

		fieldNames.remove("anotherPerson");
		fieldNames.remove("children");
		//even someTimeData can be removed but no need to do that
		iterateOneLevel("", props, outputAsNode, fieldNames);
		iterateOneLevel("anotherPerson.", props, (ObjectNode) outputAsNode.get("anotherPerson"), fieldNames);
		//(ObjectNode) ((ArrayNode) outputAsNode.get("children"))
		ArrayNode children= (ArrayNode) outputAsNode.get("children");
		ObjectNode target=children!=null?(ObjectNode) children.get(0):null;
		iterateOneLevel("children[0].", props, target, fieldNames);
		//assertEquals(inputAsNode, outputAsNode);//instead we are using the iterate methods above
		for (Tuple<OffsetDateTime, OffsetDateTime> tuple : list) {
			assertEquals(tuple.getX(),tuple.getY());
		}
		return list;
		
	}

	private void iterateOneLevel(String propKeyPrefix, Properties props, ObjectNode outputAsNode, Set<String> fieldNames) {
		if(outputAsNode!=null)
		{
			for (String key : fieldNames) {
				String inputVal = props.getProperty(propKeyPrefix+key);
				JsonNode jsonNode = outputAsNode.get(key);
				if(jsonNode!=null)
				{
					if(jsonNode instanceof TextNode)
					{
						TextNode txtNode=(TextNode) jsonNode;
						String outputVal=txtNode.asText();
						assertEquals(inputVal,outputVal, "for key="+propKeyPrefix+key);
					}
					else if(jsonNode instanceof IntNode)
					{
						IntNode intNode=(IntNode) jsonNode;
						int asInt = intNode.asInt();
						assertEquals(inputVal,String.valueOf(asInt), "for key="+propKeyPrefix+key);
					}
					else
					{
						System.err.println("also got "+jsonNode.getClass().getName());
					}
				}
				
			}
		}
		
	}
	
	private Properties getFormJsonAsProperties(String pathInCp) throws IOException 
	{
		Resource resource = resourceLoader.getResource(
			      "classpath:"+pathInCp);
		Properties props= new Properties();
		try(InputStream inputStream = resource.getInputStream())
		{
			props.load(inputStream);
		}
		
		return props;
		
		
	}
	ObjectMapper om = new ObjectMapper();
	private JsonNode jsonStringToJsonNode(String json) throws JsonMappingException, JsonProcessingException
	{
		return om.readTree(json);
	}
		
	private Properties invalidAgeInNested(Properties x){
		x.setProperty("age", "17");
		x.setProperty("anotherPerson.age", "17");
		x.setProperty("children[0].age", "17");
		return x;
		
	}
	
	private Properties invalidAge(Properties x){
		x.setProperty("age", "17");
		return x;
	};
	
	private Properties invalidCard(Properties x){
		x.setProperty("creditCardNumber", "44444444444444");
		return x;
	};
	
	private void badRequest( String urlSubPath, String inputPathInCp, UnaryOperator<Properties> s, String pathOfExpectationInCp) throws IOException, JsonMappingException, JsonProcessingException {
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
		Properties  props = getFormJsonAsProperties(inputPathInCp);
		props=s.apply(props);
		HttpHeaders headers=new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	    MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
	    Set<Object> keySet = props.keySet();
	    for (Object keyObject : keySet) {
			String key=(String) keyObject;
			String val=props.getProperty(key);
			map.add(key, val);
		}
	    

	    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

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
		ObjectNode expectedResponseBodyNode = (ObjectNode) jsonStringToJsonNode(expectedResponseBodyJson);
		assertEquals(expectedResponseBodyNode,outputAsJsonNode);
	}
	
	private JsonNode getJsonNode(String pathInClassPath) throws IOException, JsonMappingException, JsonProcessingException {
		String input = getJsonAsString(pathInClassPath);
		JsonNode jsonStringToJsonNode = jsonStringToJsonNode(input);
		return jsonStringToJsonNode;
	}
	
	private String getJsonAsString(String pathInCp) throws IOException 
	{
		Resource resource = resourceLoader.getResource(
			      "classpath:"+pathInCp);
		return resource.getContentAsString(StandardCharsets.UTF_8);
		
		
	}
	

	


	
	/*
	 *

	 
	 @Test
		@Disabled("support for custom like cc must be added")
		void savePersonJsonWithInvalidCCTest() throws Exception {
			badRequest("saveperson/", "examples/1.form.properties", this::invalidCard, "errors/badcc.json");
		}
		
		@Test
		void savePersonJsonWithInvalidAgeTest() throws Exception {
			badRequest("saveperson/", "examples/1.form.properties", this::invalidAge, "errors/invalidAgeForm.json");
		}
		


		@Test
		@Disabled("support for nested must be added")
		void saveNestedPersonJsonWithInvalidAgeTest() throws Exception {
			badRequest("saveperson/", "examples/2.form.properties", this::invalidAgeInNested, "errors/invalidAgeNested.json");
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
	void saveNestedPersonJsonWithInvalidCCTest() throws Exception {
		badRequest("saveperson/", "examples/2.json", this::invalidCardsInNested, "errors/badCCsInNested.json");
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
	
	private ObjectNode invalidCardAgeEmail1InNested(ObjectNode x){
		 invalidNested(x, this::invalidCardAgeEmail1);
		return x;
		
	}
	
	private ObjectNode invalidCardAgeEmail1(ObjectNode x){
		
		return invalidAge(invalidEmail1(invalidCard(x)));
	};
	
	@Test
	void saveNestedPersonJsonWithInvalidCardAgeEmail1Test() throws Exception {
		badRequest("saveperson/", "examples/2.json", this::invalidCardAgeEmail1InNested, "errors/invalidCCAgeEmail1Nested.json");
	}
	
		
	
	
	*/

}

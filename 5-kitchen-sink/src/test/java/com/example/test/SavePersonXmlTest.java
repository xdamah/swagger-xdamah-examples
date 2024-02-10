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
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.example.custom.SampleCustomType;
import com.example.model.Person;
import com.example.test.model.BridgePerson;
import com.example.test.model.Tuple;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
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
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SavePersonXmlTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	ResourceLoader resourceLoader;

	@Autowired
	private MappingJackson2XmlHttpMessageConverter mappingJackson2XmlHttpMessageConverter;
	/**
	 * just trying to ensure the bridge follows these rules and is otherwise a copy.
	 * Not exhaustive
	 * @throws Exception
	 */
	@Test
	void bridgeTest() throws Exception
	{
		Class bridgeClass=BridgePerson.class;
		Class personClass=Person.class;
		Field[] bridgeFields = bridgeClass.getDeclaredFields();
		Field[] personFields = personClass.getDeclaredFields();
		assertTrue(bridgeFields.length==personFields.length);
		
		Class[] allowedDifferentFieldTypes= {LocalDate.class,
				SampleCustomType.class, OffsetDateTime.class,
				Person.class};
		
		for (Field field : personFields) {
			Field bridgeField = bridgeClass.getDeclaredField(field.getName());
			Class<?> fieldType = field.getType();
			Class<?> bridgeFieldType = bridgeField.getType();
			boolean found=false;
			for (Class class1 : allowedDifferentFieldTypes) {
				if(fieldType==class1)
				{
					found=true;
					break;
				}
			}
			if(found)
			{
				if(fieldType==Person.class)
				{
					assertTrue(BridgePerson.class==bridgeFieldType);
				}
				else
				{
					assertTrue(String.class==bridgeFieldType);
				}
			}
			else
			{
				assertTrue(fieldType==bridgeFieldType);
			}
			
			
		}
		
		
		
	}
	
	

	@Test
	void savePersonXmlTest() throws Exception {

		List<Tuple<OffsetDateTime, OffsetDateTime>> list = saveXml("person/id1?def=18&defArr=1&defArr=2&defArr=3&x=2024-01-12", "examples/1.xml", this::f1);
		assertEquals(1, list.size());
	}
	
	@Test
	void saveNestedPersonXmlTest() throws Exception {

		List<Tuple<OffsetDateTime, OffsetDateTime>> list = saveXml("person/id1?def=18&defArr=1&defArr=2&defArr=3&x=2024-01-12", "examples/2.xml", this::f2);
		assertEquals(3, list.size());
	}
	@Test
	void savePersonaXmlTest() throws Exception {

		List<Tuple<OffsetDateTime, OffsetDateTime>> list = saveXml("persona/id1?def=18&defArr=1&defArr=2&defArr=3&x=2024-01-12", "examples/1.xml", this::f1);
		assertEquals(1, list.size());
	}
	
	@Test
	void saveNestedPersonaXmlTest() throws Exception {

		List<Tuple<OffsetDateTime, OffsetDateTime>> list = saveXml("persona/id1?def=18&defArr=1&defArr=2&defArr=3&x=2024-01-12", "examples/2.xml", this::f2);
		assertEquals(3, list.size());
	}
	
	@Test
	void savePersonbXmlTest() throws Exception {

		List<Tuple<OffsetDateTime, OffsetDateTime>> list = saveXml("personb/id1?def=18&defArr=1&defArr=2&defArr=3&x=2024-01-12", "examples/1.xml", this::f1);
		assertEquals(1, list.size());
	}
	
	@Test
	void saveNestedPersonbXmlTest() throws Exception {

		List<Tuple<OffsetDateTime, OffsetDateTime>> list = saveXml("personb/id1?def=18&defArr=1&defArr=2&defArr=3&x=2024-01-12", "examples/2.xml", this::f2);
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
	void savePersonStringBodyXmlTest() throws Exception {
	
		List<Tuple<OffsetDateTime, OffsetDateTime>> list = saveXml("stringreqbody/id1?def=18&defArr=1&defArr=2&defArr=3&x=2024-01-12", "examples/1.xml", this::f1);
		assertEquals(1, list.size());
	}
	
	@Test
	void saveNestedPersonStringBodyXmlTest() throws Exception {
	
		List<Tuple<OffsetDateTime, OffsetDateTime>> list = saveXml("stringreqbody/id1?def=18&defArr=1&defArr=2&defArr=3&x=2024-01-12", "examples/2.xml", this::f2);
		assertEquals(3, list.size());
	}
	
	
	
	@Test
	void anotherControllerPostUsingPathTest() throws Exception {
	
		List<Tuple<OffsetDateTime, OffsetDateTime>> list = saveSimplerXml("abc/abc", "examples/1.xml", this::f1,
				(BridgePerson p)->p.setFirstName("abc"));
		assertEquals(1, list.size());
	}
	
	@Test
	void anotherControllerNestedPostUsingPathTest() throws Exception {
	
		List<Tuple<OffsetDateTime, OffsetDateTime>> list = saveSimplerXml("abc/abc", "examples/2.xml", this::f2,
				(BridgePerson p)->p.setFirstName("abc"));
		assertEquals(3, list.size());
	}
	
	@Test
	void defControllerPostUsingPathTest() throws Exception {
	
		List<Tuple<OffsetDateTime, OffsetDateTime>> list = saveSimplerXml("def?abc=19", "examples/1.xml", this::f1,
				(BridgePerson p)->p.setId(19l));
				//"id", 19);
		assertEquals(1, list.size());
	}
	
	@Test
	void defControllerNestedPostUsingPathTest() throws Exception {
	
		List<Tuple<OffsetDateTime, OffsetDateTime>> list = saveSimplerXml("def?abc=19", "examples/2.xml", this::f2, 
				(BridgePerson p)->p.setId(19l));
				//"id", 19);
		assertEquals(3, list.size());
	}
	
	@Test
	void savePersonXmlWithInvalidAgeAndOtherInvalidTest() throws Exception {
		badRequest("person/i?def=17&defArr=1&defArr=2&defArr=3&x=2024-01-12", "examples/1.xml", this::invalidAge, "errors/invalidAgeAndOtherParamsXml.json");
	}
	
	@Test
	void saveNestedPersonXmlWithInvalidAgeAndOtherInvalidParamTest() throws Exception {
		badRequest("person/i?def=17&defArr=1&defArr=2&defArr=3&x=2024-01-12", "examples/2.xml", this::invalidAgeInNested, "errors/invalidAgeAndOtherParamsNestedXml.json");
	}
	
	@Test
	void savePersonaXmlWithInvalidAgeAndOtherInvalidTest() throws Exception {
		badRequest("persona/i?def=17&defArr=1&defArr=2&defArr=3&x=2024-01-12", "examples/1.xml", this::invalidAge, "errors/invalidAgeAndOtherParamsXml.json");
	}
	
	@Test
	void saveNestedPersonaXmlWithInvalidAgeAndOtherInvalidParamTest() throws Exception {
		badRequest("persona/i?def=17&defArr=1&defArr=2&defArr=3&x=2024-01-12", "examples/2.xml", this::invalidAgeInNested, "errors/invalidAgeAndOtherParamsNestedXml.json");
	}
	
	@Test
	void savePersonbXmlWithInvalidAgeAndOtherInvalidTest() throws Exception {
		badRequest("personb/i?def=17&defArr=1&defArr=2&defArr=3&x=2024-01-12", "examples/1.xml", this::invalidAge, "errors/invalidAgeAndOtherParamsXml.json");
	}
	
	@Test
	void saveNestedPersonbXmlWithInvalidAgeAndOtherInvalidParamTest() throws Exception {
		badRequest("personb/i?def=17&defArr=1&defArr=2&defArr=3&x=2024-01-12", "examples/2.xml", this::invalidAgeInNested, "errors/invalidAgeAndOtherParamsNestedXml.json");
	}


	
	private Consumer<BridgePerson> c= (BridgePerson p)->p.setFirstName("abc");
	
	private List<Tuple<OffsetDateTime, OffsetDateTime>> saveSimplerXml(String urlSubPath, String inputPathInCp,
			BiFunction<Element, ObjectNode, List<Tuple<OffsetDateTime, OffsetDateTime>>> f, Consumer<BridgePerson> c) throws IOException,
			JsonMappingException, JsonProcessingException, ParserConfigurationException, SAXException, TransformerFactoryConfigurationError, TransformerException {
		String input = getContentAsString(inputPathInCp);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_XML);
		
		HttpEntity<String> request = new HttpEntity<String>(input, headers);
		ResponseEntity<String> response = this.restTemplate.postForEntity("http://localhost:" + port + "/" + urlSubPath,
				request, String.class);
		HttpStatusCode statusCode = response.getStatusCode();
		assertEquals( HttpStatus.OK.value(), statusCode.value());
		String output = response.getBody();
		Document doc = docFromStringContent(input);
		System.out.println("doc="+doc);
		Element inputRootElement = doc.getDocumentElement();
		inputRootElement.normalize();

		ObjectNode outputAsNode = (ObjectNode) jsonStringToJsonNode(output);

		List<Tuple<OffsetDateTime, OffsetDateTime>> list = f.apply(inputRootElement, outputAsNode);
		//cant do the next
		//assertEquals(inputRootElement, outputAsNode);
		System.out.println("inputRootElement="+inputRootElement);
		BridgePerson inputPerson = xmlDocToBridgePojo(doc);
		System.out.println("inputPerson="+inputPerson);
		BridgePerson outputPerson = objectNodeToBridgePojo(outputAsNode);
		System.out.println("outputPerson="+outputPerson);
		
		//inputPerson.setFirstName("abc");//from path can avoid hardcode later if needed
		c.accept(inputPerson);
		
		assertEquals(inputPerson, outputPerson);
		for (Tuple<OffsetDateTime, OffsetDateTime> tuple : list) {
			assertEquals(tuple.getX(), tuple.getY());
		}
		return list;

	}
	private void savePersonAndGetPicInternal(String urlSubPath,  MediaType acceptedType) throws IOException, ParserConfigurationException, SAXException {
		String input = getContentAsString("examples/2.xml");
		Document doc = docFromStringContent(input);
		Element root = doc.getDocumentElement();
		Element pic=getChild(root, "pic");
		String picContent=getContent(pic);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_XML);
		if(acceptedType!=null)
		{
			List<MediaType> accepts= new ArrayList<>();
			accepts.add(acceptedType);
			headers.setAccept(accepts);
		}
		HttpEntity<String> request = new HttpEntity<String>(input, headers);
		ResponseEntity<byte[]> response = this.restTemplate.postForEntity("http://localhost:" + port + "/" + urlSubPath,
				request, byte[].class);
		HttpStatusCode statusCode = response.getStatusCode();
		assertEquals(statusCode.value(), HttpStatus.OK.value());
		byte[] body = response.getBody();
		String encodedPic = Base64.getEncoder().encodeToString(body);
		System.out.println(picContent.equals(encodedPic));
		assertEquals(picContent, encodedPic);
	}
	/*
	 * 
	 * WIP
	 

	

	

	@Test
	void saveNestedPersonJsonWithInvalidEmail1Test() throws Exception {
		badRequest("saveperson/", "examples/2.xml", this::invalidEmail1InNested, "errors/invalidEmail1Nested.json");
	}

	@Test
	void savePersonJsonWithInvalidEmail1Test() throws Exception {
		badRequest("saveperson/", "examples/1.xml", this::invalidEmail1, "errors/invalidEmail1.json");
	}

	@Test
	void savePersonJsonWithInvalidCardAgeEmail1Test() throws Exception {
		badRequest("saveperson/", "examples/1.xml", this::invalidCardAgeEmail1, "errors/invalidCCAgeEmail1Xml.json");
	}


	@Test
	void saveNestedPersonJsonWithInvalidCardAgeEmail1Test() throws Exception {
		badRequest("saveperson/", "examples/2.xml", this::invalidCardAgeEmail1InNested,
				"errors/invalidCCAgeEmail1NestedXml.json");
	}
	


	

	

	

	
	

	@Test
	void savePersonJsonWithInvalidCCTest() throws Exception {
		badRequest("saveperson/", "examples/1.xml", this::invalidCard, "errors/badcc.json");
	}


	@Test
	void saveNestedPersonJsonWithInvalidCCTest() throws Exception {
		badRequest("saveperson/", "examples/2.xml", this::invalidCardsInNested, "errors/badCCsInNested.json");
	}

	*/
	private Element invalidCard(Element x) {

		replaceXmlChildElementValue(x, "creditCardNumber", "44444444444444");
		return x;
	}

	private void replaceXmlChildElementValue(Element x, String elementName, String value) {
		Element child = getChild(x, elementName);
		Node parent = child.getParentNode();
		
		Document doc = x.getOwnerDocument();
		Element newChild = doc.createElement(elementName);
		
		newChild.setTextContent(value);
		parent.replaceChild(newChild, child);
	};

	
	private Element invalidCardsInNested(Element x) {
		invalidNested(x, this::invalidCard);
		return x;

	}

	private void invalidNested(Element x, UnaryOperator<Element> s) {
		s.apply(x);
		s.apply(getChild(x, "anotherPerson"));
		Element childrenElement = getChild( x,"children");
		//for now we assume only one row 
		Element childPerson = getChild(childrenElement, "person");
		s.apply(childPerson);
	};
	
	private Element invalidAge(Element x) {
		replaceXmlChildElementValue(x, "age", "17");

		return x;
	};

	private Element invalidAgeInNested(Element x) {
		invalidNested(x, this::invalidAge);
		return x;

	}

	

	private Element invalidEmail1(Element x) {
		replaceXmlChildElementValue(x, "email1", "abcxabc.com");

		return x;
	};

	private Element invalidEmail1InNested(Element x) {
		invalidNested(x, this::invalidEmail1);
		return x;

	}
	
	
	private Element invalidCardAgeEmail1InNested(Element x) {
		invalidNested(x, this::invalidCardAgeEmail1);
		return x;

	}
	
	private Element invalidCardAgeEmail1(Element x) {

		return invalidAge(invalidEmail1(invalidCard(x)));
	};
	
		
	
	public static Element getChild(Element parent, String name) {
		for (Node child = parent.getFirstChild(); child != null; child = child.getNextSibling()) {
			if (child instanceof Element && name.equals(child.getNodeName())) {
				return (Element) child;
			}
		}
		return null;
	}

	public static String getContent(Element element) {
		NodeList nl = element.getChildNodes();
		StringBuffer content = new StringBuffer();
		for (int i = 0; i < nl.getLength(); i++) {
			Node node = nl.item(i);
			switch (node.getNodeType()) {
			case Node.ELEMENT_NODE:
				// return node;
			case Node.CDATA_SECTION_NODE:
			case Node.TEXT_NODE:
				content.append(node.getNodeValue());
				break;
			}
		}
		return content.toString().trim();
	}

	private List<Tuple<OffsetDateTime, OffsetDateTime>> f1(Element inputAsElement,  ObjectNode outputAsNode) {
		List<Tuple<OffsetDateTime, OffsetDateTime>> list = new ArrayList<>();
		String inputSomeTimeData = getElementContent(inputAsElement, "someTimeData");

		System.out.println("inputSomeTimeData=" + inputSomeTimeData);
		String outputSomeTimeData = outputAsNode.get("someTimeData").asText();

		System.out.println("outputSomeTimeData=" + outputSomeTimeData);

		removeChildElement(inputAsElement, "someTimeData");
		outputAsNode.remove("someTimeData");

		System.out.println("inputt=" + inputAsElement);
		System.out.println("output=" + outputAsNode);
		OffsetDateTime d1 = OffsetDateTime.parse(inputSomeTimeData);
		OffsetDateTime d2 = OffsetDateTime.parse(outputSomeTimeData);
		d1 = d1.withOffsetSameInstant(ZoneOffset.ofHours(0));
		d2 = d2.withOffsetSameInstant(ZoneOffset.ofHours(0));
		list.add(new Tuple<OffsetDateTime, OffsetDateTime>(d1, d2));
		return list;
	}

	private void removeChildElement(Element inputAsElement, String childElementName) {
		Element childElement = getChild(inputAsElement, childElementName);
		if (childElement != null) {
			Node parent = childElement.getParentNode();
			parent.removeChild(childElement);
			
			parent.normalize();
		}
	}

	private String getElementContent(Element inputAsElement, String name) {
		Element childElement = getChild(inputAsElement, name);
		String content = null;
		if (childElement != null) {
			content = getContent(childElement);
		}
		return content;
	}

	private List<Tuple<OffsetDateTime, OffsetDateTime>> f2(Element inputAsNode, ObjectNode outputAsNode) {
		List<Tuple<OffsetDateTime, OffsetDateTime>> list = f1(inputAsNode, outputAsNode);
		List<Tuple<OffsetDateTime, OffsetDateTime>> list1 = f1(getChild( inputAsNode,"anotherPerson"),
				(ObjectNode) outputAsNode.get("anotherPerson"));
		
		Element childrenElement = getChild( inputAsNode,"children");
		//for now we assume only one row 
		Element childPerson = getChild(childrenElement, "person");
		//childrenElement.
		List<Tuple<OffsetDateTime, OffsetDateTime>> list2 = f1(childPerson,
				(ObjectNode) ((ArrayNode) outputAsNode.get("children")).get(0));
		
		list.addAll(list1);
		list.addAll(list2);
		return list;
	}


	

	
	private void badRequest(String urlSubPath, String inputPathInCp, UnaryOperator<Element> s,
			String pathOfExpectationInCp) throws IOException, JsonMappingException, JsonProcessingException, ParserConfigurationException, SAXException, TransformerFactoryConfigurationError, TransformerException {
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
		System.out.println("use="+use);
		System.out.println("use1="+use1);
		Document doc = docFromInputPathInCp(inputPathInCp);
		Element root = doc.getDocumentElement();
		root = s.apply(root);
		String input =docWriteToString(doc);
		System.out.println("chaged input="+input);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_XML);
		HttpEntity<String> request = new HttpEntity<String>(input, headers);
		ResponseEntity<String> response = this.restTemplate.postForEntity("http://localhost:" + port + "/" + urlSubPath,
				request, String.class);
		HttpStatusCode statusCode = response.getStatusCode();
		System.out.println("statusCode=" + statusCode);
		assertNotEquals(statusCode.value(), HttpStatus.OK.value());
		assertEquals(statusCode.value(), HttpStatus.BAD_REQUEST.value());
		String output = response.getBody();
		System.out.println("output=" + output);
		ObjectNode outputAsJsonNode = (ObjectNode) jsonStringToJsonNode(output);
		
		String expectedResponseBodyJson = getContentAsString(pathOfExpectationInCp);
		System.out.println("expectedResponseBodyJson="+expectedResponseBodyJson);
		expectedResponseBodyJson=expectedResponseBodyJson.replaceAll(Pattern.quote("/person/i"), use);
		expectedResponseBodyJson=expectedResponseBodyJson.replaceAll(Pattern.quote("/person/{id}"), use1);
		//System.out.println("modified="+expectedResponseBodyJson);
		ObjectNode expectedResponseBodyNode = (ObjectNode) jsonStringToJsonNode(expectedResponseBodyJson);
		assertEquals(expectedResponseBodyNode, outputAsJsonNode);
	}

	private Document docFromInputPathInCp(String inputPathInCp)
			throws IOException, ParserConfigurationException, SAXException {
		String xmlContent= getContentAsString(inputPathInCp);
		Document doc=docFromStringContent(xmlContent);
		return doc;
	}

	private JsonNode getJsonNode(String pathInClassPath)
			throws IOException, JsonMappingException, JsonProcessingException {
		String input = getContentAsString(pathInClassPath);
		JsonNode jsonStringToJsonNode = jsonStringToJsonNode(input);
		return jsonStringToJsonNode;
	}

	private List<Tuple<OffsetDateTime, OffsetDateTime>> saveXml(String urlSubPath, String inputPathInCp,
			BiFunction<Element, ObjectNode, List<Tuple<OffsetDateTime, OffsetDateTime>>> f) throws IOException,
			JsonMappingException, JsonProcessingException, ParserConfigurationException, SAXException, TransformerFactoryConfigurationError, TransformerException {
		String input = getContentAsString(inputPathInCp);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_XML);
		headers.set("header1", "header1val1");
		    
		headers.set("header2", "h1,h2,h3");//corersponds to style:simple:explode:false 
		headers.add("Cookie", "cookieValue1=cookieeg1");
		HttpEntity<String> request = new HttpEntity<String>(input, headers);
		ResponseEntity<String> response = this.restTemplate.postForEntity("http://localhost:" + port + "/" + urlSubPath,
				request, String.class);
		HttpStatusCode statusCode = response.getStatusCode();
		assertEquals( HttpStatus.OK.value(), statusCode.value());
		String output = response.getBody();
		Document doc = docFromStringContent(input);
		System.out.println("doc="+doc);
		Element inputRootElement = doc.getDocumentElement();
		inputRootElement.normalize();

		ObjectNode outputAsNode = (ObjectNode) jsonStringToJsonNode(output);

		List<Tuple<OffsetDateTime, OffsetDateTime>> list = f.apply(inputRootElement, outputAsNode);
		//cant do the next
		//assertEquals(inputRootElement, outputAsNode);
		System.out.println("inputRootElement="+inputRootElement);
		BridgePerson inputPerson = xmlDocToBridgePojo(doc);
		System.out.println("inputPerson="+inputPerson);
		BridgePerson outputPerson = objectNodeToBridgePojo(outputAsNode);
		System.out.println("outputPerson="+outputPerson);
		inputPerson.setRegistrationDate("2024-01-12");//from x
		assertEquals(inputPerson, outputPerson);
		for (Tuple<OffsetDateTime, OffsetDateTime> tuple : list) {
			assertEquals(tuple.getX(), tuple.getY());
		}
		return list;

	}



	private BridgePerson objectNodeToBridgePojo(ObjectNode outputAsNode)
			throws JsonProcessingException, JsonMappingException {
		String string = objectNodeToString(outputAsNode);
		BridgePerson outputPerson =om.readValue(string, BridgePerson.class);
		return outputPerson;
	}



	private String objectNodeToString(ObjectNode outputAsNode) throws JsonProcessingException {
		return om.writeValueAsString(outputAsNode);
	}



	private BridgePerson xmlDocToBridgePojo(Document doc)
			throws JsonProcessingException, JsonMappingException, TransformerFactoryConfigurationError, TransformerException {
		String modifiedInputAsString = docWriteToString(doc);
		
		BridgePerson person=xmlContentToBridgePojo(modifiedInputAsString);
		return person;
	}



	private BridgePerson xmlContentToBridgePojo(String modifiedInputAsString)
			throws JsonProcessingException, JsonMappingException {
		XmlMapper xmlMapper = (XmlMapper) mappingJackson2XmlHttpMessageConverter.getObjectMapper();
		BridgePerson xmlPerson = xmlMapper.readValue(modifiedInputAsString, BridgePerson.class);
		return xmlPerson;
	}



	private String docWriteToString(Document doc) throws JsonProcessingException, TransformerFactoryConfigurationError, TransformerException {
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
	    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	   
	    StreamResult result = new StreamResult(new StringWriter());
	    DOMSource source = new DOMSource(doc);
	    transformer.transform(source, result);

	    String xmlString = result.getWriter().toString();
		return xmlString;
	}

	ObjectMapper om = new ObjectMapper();

	private JsonNode jsonStringToJsonNode(String json) throws JsonMappingException, JsonProcessingException {
		return om.readTree(json);
	}

	private String getContentAsString(String pathInCp) throws IOException {
		Resource resource = resourceLoader.getResource("classpath:" + pathInCp);
		return resource.getContentAsString(StandardCharsets.UTF_8);

	}
	
	private Document docFromStringContent(String input)
			throws ParserConfigurationException, SAXException, IOException {
		System.out.println("input="+input);
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		InputSource is = new InputSource(new StringReader(input));
		Document doc = builder.parse(is);
		
		return doc;
	}


}

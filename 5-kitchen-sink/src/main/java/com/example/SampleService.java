package com.example;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.stereotype.Service;

import com.example.custom.SampleCustomType;
import com.example.model.Person;
import com.example.model.PersonParam;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class SampleService {
	private static final Logger logger = LoggerFactory.getLogger(SampleService.class);

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private MappingJackson2XmlHttpMessageConverter mappingJackson2XmlHttpMessageConverter;

	public Person doSomething(Person person, PersonParam personParam) {
		logger.debug("got person=" + person + ",param=" + personParam + "from " + request.getRequestURI());

		if (personParam.getX() != null) {
			person.setRegistrationDate(personParam.getX());
		}
		else
		{
			person.setRegistrationDate(LocalDate.now());
		}

		return person;
	}

	@Autowired
	ObjectMapper objectMapper;

	public Person stringreqbody(String body, PersonParam personParam)
			throws JsonMappingException, JsonProcessingException {
		String contentType = request.getHeader("Content-Type");
		Person p = null;
		if (contentType != null) {
			if (contentType.equals(MediaType.APPLICATION_JSON_VALUE)) {
				p = objectMapper.readValue(body, Person.class);
			} else if (contentType.equals(MediaType.APPLICATION_XML_VALUE)) {
				p = mappingJackson2XmlHttpMessageConverter.getObjectMapper().readValue(body, Person.class);
			}
		} else {

		}

		if (personParam.getX() != null) {
			if (p != null) {
				p.setRegistrationDate(personParam.getX());
			}

		}
		logger.debug("returning " + p);
		return p;
	}

	public Resource pic(Person person) {
		ByteArrayResource resource = new ByteArrayResource(person.getPic());

		return resource;
	}

	public Resource binary(byte[] bytes) {
		ByteArrayResource resource = new ByteArrayResource(bytes);

		return resource;
	}

	public Person byid(long id) {
		Person person = new Person();
		person.setId(id);
		person.setFirstName("F");
		person.setLastName("L");
		person.setRegistrationDate(LocalDate.now());
		person.setSomeTimeData(OffsetDateTime.now());
		person.setSampleCustomTypeData(new SampleCustomType("hello"));

		return person;
	}

	public Person byids(long[] ids) {
		Person person = new Person();
		if (ids != null) {
			if (ids.length > 0) {
				person.setId(ids[0]);
			}
			if (ids.length > 1) {
				person.setFirstName("F" + ids[1]);
			}
			if (ids.length > 2) {
				person.setLastName("L" + ids[2]);
			}
		}

		person.setLastName("L");
		return person;
	}

}

package com.example;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Person;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

/*
 * Just to demonstrate that we can do a mix also.
 * However as of now must manually merge its swagger specs accurately.
 */
@RestController
public class AnotherController {
	private static final Logger logger = LoggerFactory.getLogger(AnotherController.class);

	@RequestMapping(value = "/abc/{abc}", method = RequestMethod.GET)
	ResponseEntity<Map<String, String>> another(@PathVariable(name = "abc", required = true) String abc) {
		Map<String, String> map = new HashMap<>();
		if (abc != null) {
			map.put("abc", abc);
		}
		return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);

	}

	@RequestMapping(value = "/abc/{abc}", method = RequestMethod.POST)
	ResponseEntity<Person> post(@PathVariable(name = "abc", required = true) String abc, @RequestBody Person person) {

		if (abc != null) {
			person.setFirstName(abc);
		}
		return new ResponseEntity<Person>(person, HttpStatus.OK);

	}

	

}

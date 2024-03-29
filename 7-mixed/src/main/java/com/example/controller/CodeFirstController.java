package com.example.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping(value = "/codefirst/{abc}")
public class CodeFirstController {
	
	@RequestMapping( method = RequestMethod.GET)
	ResponseEntity<Map<String, String>> another(@PathVariable(name="abc", required = true) String abc)
	{
		Map<String, String> map= new HashMap<>();
		if(abc!=null)
		{
			map.put("abc", abc);
		}
		return new ResponseEntity<Map<String,String>>(map, HttpStatus.OK);
		
	}
	
	@RequestMapping( method = RequestMethod.POST)
	ResponseEntity<Person> post(@PathVariable(name="abc", required = true) String abc,
			@RequestBody Person person)
	{
		
		if(abc!=null)
		{
			person.setFirstName(abc);
		}
		return new ResponseEntity<Person>(person, HttpStatus.OK);
		
	}
	
	

}

package com.example.service;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.example.custom.SampleCustomType;
import com.example.model.Person;

@Service
public class SampleService {

	public Person savePerson(Person person) {
		return person;
	}

	public Resource pic(Person person) {
		ByteArrayResource resource = new ByteArrayResource(person.getPic());

		return resource;
	}

	

	public Person byid(long id) {
		Person person = new Person();
		person.setId(id);
		person.setFirstName("F");
		person.setLastName("L");
		person.setRegistrationDate(LocalDate.of(2024, 1, 1));
		person.setSomeTimeData(OffsetDateTime.of(2024, 1, 1, 0, 0, 0, 0, ZoneOffset.ofHours(0)));
		person.setSampleCustomTypeData(new SampleCustomType("hello"));

		return person;
	}

	

}

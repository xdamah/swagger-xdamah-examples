package com.example;

import java.time.LocalDate;
import java.time.OffsetDateTime;

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
